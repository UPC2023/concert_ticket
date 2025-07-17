package org.demo02.controller;

import org.demo02.entity.Order;
import org.demo02.service.OrderService;
import org.demo02.service.ConcertSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/order")

public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ConcertSeatService concertSeatService;
    
    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> orderData) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long userId = Long.valueOf(orderData.get("userId").toString());
            Long concertId = Long.valueOf(orderData.get("concertId").toString());
            Integer quantity = Integer.valueOf(orderData.get("quantity").toString());
            String seatInfo = null;
            if (orderData.get("seatDetail") != null) {
                seatInfo = orderData.get("seatDetail").toString(); // 优先存seatDetail
            } else if (orderData.get("seats") != null) {
                seatInfo = orderData.get("seats").toString();
            }
            java.math.BigDecimal unitPrice = orderData.get("unitPrice") != null ? new java.math.BigDecimal(orderData.get("unitPrice").toString()) : null;
            Order order = new Order(userId, concertId, quantity, unitPrice);
            order.setSeatInfo(seatInfo);
            order.setPaymentStatus("PENDING");
            order.setOrderStatus("PENDING");
            order.setPaymentMethod(null);
            order.setTotalAmount(unitPrice); // 明确赋值总价
            boolean created = orderService.saveOrder(order); // 需在OrderService中实现saveOrder
            if (created) {
                response.put("success", true);
                response.put("message", "订单创建成功");
                response.put("order", order);
            } else {
                response.put("success", false);
                response.put("message", "订单创建失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "订单创建失败：" + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取订单详情")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Order order = orderService.getOrderById(id);
            if (order != null) {
                response.put("success", true);
                response.put("order", order);
            } else {
                response.put("success", false);
                response.put("message", "订单不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "根据订单号获取订单")
    @GetMapping("/no/{orderNo}")
    public ResponseEntity<Map<String, Object>> getOrderByOrderNo(@PathVariable String orderNo) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Order order = orderService.getOrderByOrderNo(orderNo);
            if (order != null) {
                response.put("success", true);
                response.put("order", order);
            } else {
                response.put("success", false);
                response.put("message", "订单不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "支付订单")
    @PostMapping("/{id}/pay")
    public ResponseEntity<Map<String, Object>> payOrder(@PathVariable Long id, @RequestBody Map<String, String> paymentData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String paymentMethod = paymentData.get("paymentMethod");
            boolean success = orderService.payOrder(id, paymentMethod);
            if (success) {
                // 补充：更新订单状态为已支付
                Order order = orderService.getOrderById(id);
                order.setPaymentStatus("PAID");
                order.setOrderStatus("COMPLETED");
                order.setPaymentMethod(paymentMethod);
                order.setPayTime(LocalDateTime.now());
                // 新增：生成二维码
                try {
                    String qrContent = String.valueOf(order.getId());
                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
                    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
                    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
                    String qrBase64 = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
                    order.setQrCode(qrBase64);
                } catch (WriterException | java.io.IOException e) {
                    order.setQrCode(null);
                }
                orderService.updateOrder(order);
                // 新增：批量将座位状态置为已售
                if (order.getSeatInfo() != null) {
                    try {
                        String seatInfo = order.getSeatInfo();
                        List<Map<String, Object>> seatList = new com.fasterxml.jackson.databind.ObjectMapper().readValue(seatInfo, List.class);
                        for (Map<String, Object> seat : seatList) {
                            Long seatId = null;
                            Long concertId = order.getConcertId();
                            Object seatIdObj = seat.get("seatId");
                            if (seatIdObj instanceof Integer) {
                                seatId = ((Integer) seatIdObj).longValue();
                            } else if (seatIdObj instanceof Long) {
                                seatId = (Long) seatIdObj;
                            }
                            if (seatId != null && concertId != null) {
                                concertSeatService.sellSeat(concertId, seatId);
                            }
                        }
                    } catch (Exception ignore) {}
                }
                response.put("success", true);
                response.put("message", "支付成功");
            } else {
                response.put("success", false);
                response.put("message", "支付失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "支付失败：" + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "取消订单")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = orderService.cancelOrder(id);
            if (success) {
                response.put("success", true);
                response.put("message", "订单取消成功");
            } else {
                response.put("success", false);
                response.put("message", "订单取消失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "订单取消失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "退款订单")
    @PostMapping("/{id}/refund")
    public ResponseEntity<Map<String, Object>> refundOrder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = orderService.refundOrder(id);
            if (success) {
                response.put("success", true);
                response.put("message", "退款成功");
            } else {
                response.put("success", false);
                response.put("message", "退款失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "退款失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "核销票务（入场）")
    @PostMapping("/verify-ticket")
    public ResponseEntity<Map<String, Object>> verifyTicket(@RequestBody Map<String, String> verifyData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String qrCode = verifyData.get("qrCode");
            boolean success = orderService.verifyTicket(qrCode);
            if (success) {
                response.put("success", true);
                response.put("message", "票务核销成功");
            } else {
                response.put("success", false);
                response.put("message", "票务核销失败，请检查二维码是否正确或票务状态");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "票务核销失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "生成二维码")
    @GetMapping("/{id}/qr-code")
    public ResponseEntity<Map<String, Object>> generateQRCode(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String qrCode = orderService.generateQRCode(id);
            response.put("success", true);
            response.put("qrCode", qrCode);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "生成二维码失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取用户订单列表
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getOrdersByUserId(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            response.put("success", true);
            response.put("orders", orders);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取用户订单失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取所有订单（管理员功能）
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String paymentStatus,
            @RequestParam(required = false) String orderStatus) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Order> orders;
            if (orderNo != null || userId != null || paymentStatus != null || orderStatus != null) {
                // 条件查询
                Long userIdLong = userId != null ? Long.valueOf(userId) : null;
                orders = orderService.getOrdersByCondition(orderNo, userIdLong, null, paymentStatus, orderStatus);
            } else {
                // 获取所有
                orders = orderService.getAllOrders();
            }
            response.put("success", true);
            response.put("orders", orders);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 分页获取订单（管理员功能）
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getOrdersByPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = orderService.getOrdersByPage(currentPage, size);
            response.put("success", true);
            response.putAll(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 根据支付状态获取订单
    @GetMapping("/payment-status/{status}")
    public ResponseEntity<Map<String, Object>> getOrdersByPaymentStatus(@PathVariable String status) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Order> orders = orderService.getOrdersByPaymentStatus(status);
            response.put("success", true);
            response.put("orders", orders);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 根据票务状态获取订单
    @GetMapping("/ticket-status/{status}")
    public ResponseEntity<Map<String, Object>> getOrdersByTicketStatus(@PathVariable String status) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Order> orders = orderService.getOrdersByTicketStatus(status);
            response.put("success", true);
            response.put("orders", orders);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取订单统计信息（管理员功能）
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> statistics = orderService.getOrderStatistics();
            response.put("success", true);
            response.putAll(statistics);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取统计信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 删除订单（管理员功能）
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = orderService.deleteOrder(id);
            if (success) {
                response.put("success", true);
                response.put("message", "订单删除成功");
            } else {
                response.put("success", false);
                response.put("message", "订单删除失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "订单删除失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
} 