package org.demo02.service.impl;

import org.demo02.dao.OrderMapper;
import org.demo02.entity.Concert;
import org.demo02.entity.Order;
import org.demo02.service.ConcertService;
import org.demo02.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private ConcertService concertService;
    
    @Override
    @Transactional
    public Order createOrder(Long userId, Long concertId, Integer quantity) {
        Concert concert = concertService.getConcertById(concertId);
        if (concert == null) {
            throw new RuntimeException("演唱会不存在");
        }
        if (!concertService.hasEnoughSeats(concertId, quantity)) {
            throw new RuntimeException("座位不足");
        }
        // 默认不传seatInfo，单价用演唱会原价
        return createOrder(userId, concertId, quantity, null, concert.getPrice());
    }
    
    @Override
    @Transactional
    public Order createOrder(Long userId, Long concertId, Integer quantity, String seatInfo, java.math.BigDecimal unitPrice) {
        Concert concert = concertService.getConcertById(concertId);
        if (concert == null) {
            throw new RuntimeException("演唱会不存在");
        }
        if (!concertService.hasEnoughSeats(concertId, quantity)) {
            throw new RuntimeException("座位不足");
        }
        Order order = new Order(userId, concertId, quantity, unitPrice);
        order.setSeatInfo(seatInfo);
        order.setQrCode(generateQRCode(order.getId()));
        order.setOrderStatus("COMPLETED");
        order.setPaymentStatus("PAID");
        if (orderMapper.insert(order) > 0) {
            concertService.decreaseAvailableSeats(concertId, quantity);
            return order;
        }
        throw new RuntimeException("创建订单失败");
    }
    
    // 新增重载：支持支付方式
    @Transactional
    public Order createOrder(Long userId, Long concertId, Integer quantity, String seatInfo, java.math.BigDecimal unitPrice, String paymentMethod) {
        Concert concert = concertService.getConcertById(concertId);
        if (concert == null) {
            throw new RuntimeException("演唱会不存在");
        }
        if (!concertService.hasEnoughSeats(concertId, quantity)) {
            throw new RuntimeException("座位不足");
        }
        Order order = new Order(userId, concertId, quantity, unitPrice);
        order.setSeatInfo(seatInfo);
        order.setPaymentMethod(paymentMethod);
        order.setQrCode(generateQRCode(order.getId()));
        order.setOrderStatus("COMPLETED");
        order.setPaymentStatus("PAID");
        if (orderMapper.insert(order) > 0) {
            concertService.decreaseAvailableSeats(concertId, quantity);
            return order;
        }
        throw new RuntimeException("创建订单失败");
    }
    
    @Override
    public boolean deleteOrder(Long id) {
        return orderMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateOrder(Order order) {
        order.setUpdateTime(LocalDateTime.now());
        return orderMapper.update(order) > 0;
    }
    
    @Override
    public Order getOrderById(Long id) {
        return orderMapper.findById(id);
    }
    
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return orderMapper.findByOrderNo(orderNo);
    }
    
    @Override
    public List<Order> getAllOrders() {
        return orderMapper.findAll();
    }
    
    @Override
    public Map<String, Object> getOrdersByPage(int currentPage, int size) {
        Map<String, Object> result = new HashMap<>();
        
        int offset = (currentPage - 1) * size;
        List<Order> orders = orderMapper.findByPage(offset, size);
        int total = orderMapper.countTotal();
        
        result.put("orders", orders);
        result.put("total", total);
        result.put("currentPage", currentPage);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return result;
    }
    
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.findByUserId(userId);
    }
    
    @Override
    public List<Order> getOrdersByConcertId(Long concertId) {
        return orderMapper.findByConcertId(concertId);
    }
    
    @Override
    public List<Order> getOrdersByPaymentStatus(String paymentStatus) {
        return orderMapper.findByPaymentStatus(paymentStatus);
    }
    
    @Override
    public List<Order> getOrdersByOrderStatus(String orderStatus) {
        return orderMapper.findByOrderStatus(orderStatus);
    }
    
    @Override
    public List<Order> getOrdersByTicketStatus(String ticketStatus) {
        return orderMapper.findByTicketStatus(ticketStatus);
    }
    
    @Override
    public List<Order> getOrdersByPaymentMethod(String paymentMethod) {
        return orderMapper.findByPaymentMethod(paymentMethod);
    }
    
    @Override
    public List<Order> getOrdersByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return orderMapper.findByTimeRange(startTime, endTime);
    }
    
    @Override
    public List<Order> getOrdersByAmountRange(Double minAmount, Double maxAmount) {
        return orderMapper.findByAmountRange(minAmount, maxAmount);
    }
    
    @Override
    @Transactional
    public boolean payOrder(Long orderId, String paymentMethod) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return false;
        }
        
        if (!"PENDING".equals(order.getPaymentStatus())) {
            return false;
        }
        
        // 模拟支付处理
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus("PAID");
        order.setOrderStatus("CONFIRMED");
        order.setPayTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        return orderMapper.update(order) > 0;
    }
    
    @Override
    @Transactional
    public boolean cancelOrder(Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return false;
        }
        
        if ("PAID".equals(order.getPaymentStatus())) {
            // 已支付的订单需要退款
            return refundOrder(orderId);
        } else {
            // 未支付的订单直接取消
            order.setOrderStatus("CANCELLED");
            order.setUpdateTime(LocalDateTime.now());
            
            // 恢复座位
            concertService.increaseAvailableSeats(order.getConcertId(), order.getQuantity());
            
            return orderMapper.update(order) > 0;
        }
    }
    
    @Override
    @Transactional
    public boolean refundOrder(Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null || !"PAID".equals(order.getPaymentStatus())) {
            return false;
        }
        
        // 模拟退款处理
        order.setPaymentStatus("REFUNDED");
        order.setOrderStatus("CANCELLED");
        order.setUpdateTime(LocalDateTime.now());
        
        // 恢复座位
        concertService.increaseAvailableSeats(order.getConcertId(), order.getQuantity());
        
        return orderMapper.update(order) > 0;
    }
    
    @Override
    public boolean verifyTicket(String qrCode) {
        // 根据二维码查找订单
        List<Order> orders = orderMapper.findAll();
        for (Order order : orders) {
            if (qrCode.equals(order.getQrCode())) {
                if ("UNUSED".equals(order.getTicketStatus()) && "PAID".equals(order.getPaymentStatus())) {
                    // 核销票务
                    order.setTicketStatus("USED");
                    order.setUseTime(LocalDateTime.now());
                    order.setUpdateTime(LocalDateTime.now());
                    return orderMapper.update(order) > 0;
                }
                return false;
            }
        }
        return false;
    }
    
    @Override
    public String generateQRCode(Long orderId) {
        // 生成唯一的二维码
        return "QR_" + orderId + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    @Override
    public boolean updatePaymentStatus(Long id, String paymentStatus, LocalDateTime payTime) {
        return orderMapper.updatePaymentStatus(id, paymentStatus, payTime) > 0;
    }
    
    @Override
    public boolean updateOrderStatus(Long id, String orderStatus) {
        return orderMapper.updateOrderStatus(id, orderStatus) > 0;
    }
    
    @Override
    public boolean updateTicketStatus(Long id, String ticketStatus, LocalDateTime useTime) {
        return orderMapper.updateTicketStatus(id, ticketStatus, useTime) > 0;
    }
    
    @Override
    public int getOrderCount() {
        return orderMapper.countTotal();
    }
    
    @Override
    public int getOrderCountByUserId(Long userId) {
        return orderMapper.countByUserId(userId);
    }
    
    @Override
    public int getOrderCountByConcertId(Long concertId) {
        return orderMapper.countByConcertId(concertId);
    }
    
    @Override
    public List<Order> getOrdersByCondition(String orderNo, Long userId, Long concertId, String paymentStatus, String orderStatus) {
        return orderMapper.findByCondition(orderNo, userId, concertId, paymentStatus, orderStatus);
    }
    
    @Override
    public Map<String, Object> getOrderStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总订单数
        statistics.put("totalOrders", getOrderCount());
        
        // 各状态订单数
        statistics.put("pendingOrders", getOrdersByPaymentStatus("PENDING").size());
        statistics.put("paidOrders", getOrdersByPaymentStatus("PAID").size());
        statistics.put("refundedOrders", getOrdersByPaymentStatus("REFUNDED").size());
        
        // 各票务状态数
        statistics.put("unusedTickets", getOrdersByTicketStatus("UNUSED").size());
        statistics.put("usedTickets", getOrdersByTicketStatus("USED").size());
        statistics.put("expiredTickets", getOrdersByTicketStatus("EXPIRED").size());
        
        return statistics;
    }

    @Override
    public boolean saveOrder(Order order) {
        return orderMapper.insert(order) > 0;
    }

    @Override
    public List<Order> getPendingOrders() {
        return orderMapper.selectByPaymentStatus("PENDING");
    }
} 