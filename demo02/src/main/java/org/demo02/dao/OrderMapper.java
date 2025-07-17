package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo02.entity.Order;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 插入订单
    int insert(Order order);
    
    // 根据ID删除订单
    int deleteById(Long id);
    
    // 更新订单信息
    int update(Order order);
    
    // 根据ID查询订单
    Order findById(Long id);
    
    // 根据订单号查询订单
    Order findByOrderNo(String orderNo);
    
    // 查询所有订单
    List<Order> findAll();
    
    // 分页查询订单
    List<Order> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    // 根据用户ID查询订单
    List<Order> findByUserId(Long userId);
    
    // 根据演唱会ID查询订单
    List<Order> findByConcertId(Long concertId);
    
    // 根据支付状态查询订单
    List<Order> findByPaymentStatus(String paymentStatus);
    
    // 根据订单状态查询订单
    List<Order> findByOrderStatus(String orderStatus);
    
    // 根据票务状态查询订单
    List<Order> findByTicketStatus(String ticketStatus);
    
    // 根据支付方式查询订单
    List<Order> findByPaymentMethod(String paymentMethod);
    
    // 根据时间范围查询订单
    List<Order> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                @Param("endTime") LocalDateTime endTime);
    
    // 根据金额范围查询订单
    List<Order> findByAmountRange(@Param("minAmount") Double minAmount, 
                                  @Param("maxAmount") Double maxAmount);
    
    // 更新支付状态
    int updatePaymentStatus(@Param("id") Long id, @Param("paymentStatus") String paymentStatus, 
                           @Param("payTime") LocalDateTime payTime);
    
    // 更新订单状态
    int updateOrderStatus(@Param("id") Long id, @Param("orderStatus") String orderStatus);
    
    // 更新票务状态
    int updateTicketStatus(@Param("id") Long id, @Param("ticketStatus") String ticketStatus, 
                          @Param("useTime") LocalDateTime useTime);
    
    // 统计订单总数
    int countTotal();
    
    // 统计用户订单数
    int countByUserId(Long userId);
    
    // 统计演唱会订单数
    int countByConcertId(Long concertId);
    
    // 根据条件查询订单
    List<Order> findByCondition(@Param("orderNo") String orderNo, 
                                @Param("userId") Long userId, 
                                @Param("concertId") Long concertId, 
                                @Param("paymentStatus") String paymentStatus, 
                                @Param("orderStatus") String orderStatus);

    List<Order> selectByPaymentStatus(String paymentStatus);
} 