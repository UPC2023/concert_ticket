package org.demo02.service;

import org.demo02.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    // 创建订单
    Order createOrder(Long userId, Long concertId, Integer quantity);
    
    // 创建订单（重载，支持座位和单价）
    Order createOrder(Long userId, Long concertId, Integer quantity, String seatInfo, java.math.BigDecimal unitPrice);
    
    // 创建订单（重载，支持座位、单价、支付方式）
    Order createOrder(Long userId, Long concertId, Integer quantity, String seatInfo, java.math.BigDecimal unitPrice, String paymentMethod);
    
    // 删除订单
    boolean deleteOrder(Long id);
    
    // 更新订单信息
    boolean updateOrder(Order order);
    
    // 根据ID查询订单
    Order getOrderById(Long id);
    
    // 根据订单号查询订单
    Order getOrderByOrderNo(String orderNo);
    
    // 查询所有订单
    List<Order> getAllOrders();
    
    // 分页查询订单
    Map<String, Object> getOrdersByPage(int currentPage, int size);
    
    // 根据用户ID查询订单
    List<Order> getOrdersByUserId(Long userId);
    
    // 根据演唱会ID查询订单
    List<Order> getOrdersByConcertId(Long concertId);
    
    // 根据支付状态查询订单
    List<Order> getOrdersByPaymentStatus(String paymentStatus);
    
    // 根据订单状态查询订单
    List<Order> getOrdersByOrderStatus(String orderStatus);
    
    // 根据票务状态查询订单
    List<Order> getOrdersByTicketStatus(String ticketStatus);
    
    // 根据支付方式查询订单
    List<Order> getOrdersByPaymentMethod(String paymentMethod);
    
    // 根据时间范围查询订单
    List<Order> getOrdersByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    // 根据金额范围查询订单
    List<Order> getOrdersByAmountRange(Double minAmount, Double maxAmount);
    
    // 支付订单
    boolean payOrder(Long orderId, String paymentMethod);
    
    // 取消订单
    boolean cancelOrder(Long orderId);
    
    // 退款订单
    boolean refundOrder(Long orderId);
    
    // 核销票务（入场）
    boolean verifyTicket(String qrCode);
    
    // 生成二维码
    String generateQRCode(Long orderId);
    
    // 更新支付状态
    boolean updatePaymentStatus(Long id, String paymentStatus, LocalDateTime payTime);
    
    // 更新订单状态
    boolean updateOrderStatus(Long id, String orderStatus);
    
    // 更新票务状态
    boolean updateTicketStatus(Long id, String ticketStatus, LocalDateTime useTime);
    
    // 统计订单总数
    int getOrderCount();
    
    // 统计用户订单数
    int getOrderCountByUserId(Long userId);
    
    // 统计演唱会订单数
    int getOrderCountByConcertId(Long concertId);
    
    // 根据条件查询订单
    List<Order> getOrdersByCondition(String orderNo, Long userId, Long concertId, String paymentStatus, String orderStatus);
    
    // 获取订单统计信息
    Map<String, Object> getOrderStatistics();
    
    // 新增：保存订单（未支付）
    boolean saveOrder(Order order);
    
    // 获取所有未支付订单
    List<Order> getPendingOrders();
} 