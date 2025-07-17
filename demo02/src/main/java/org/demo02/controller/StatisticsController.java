package org.demo02.controller;

import org.demo02.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "数据统计")
@RestController
@RequestMapping("/api/statistics")

public class StatisticsController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConcertService concertService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AnnouncementService announcementService;
    
    @Operation(summary = "获取系统总体统计信息")
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getSystemOverview() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> overview = new HashMap<>();
            
            // 用户统计
            overview.put("totalUsers", userService.getUserCount());
            overview.put("activeUsers", userService.getUsersByStatus("ACTIVE").size());
            overview.put("inactiveUsers", userService.getUsersByStatus("INACTIVE").size());
            
            // 演唱会统计
            overview.put("totalConcerts", concertService.getConcertCount());
            overview.put("upcomingConcerts", concertService.getConcertsByStatus("UPCOMING").size());
            overview.put("ongoingConcerts", concertService.getConcertsByStatus("ONGOING").size());
            overview.put("finishedConcerts", concertService.getConcertsByStatus("FINISHED").size());
            
            // 订单统计
            Map<String, Object> orderStats = orderService.getOrderStatistics();
            overview.putAll(orderStats);
            
            // 公告统计
            overview.put("totalAnnouncements", announcementService.getAnnouncementCount());
            overview.put("publishedAnnouncements", announcementService.getPublishedAnnouncements().size());
            overview.put("activeAnnouncements", announcementService.getActiveAnnouncements().size());
            
            response.put("success", true);
            response.put("overview", overview);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取统计信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取用户统计信息")
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> userStats = new HashMap<>();
            
            userStats.put("totalUsers", userService.getUserCount());
            userStats.put("activeUsers", userService.getUsersByStatus("ACTIVE").size());
            userStats.put("inactiveUsers", userService.getUsersByStatus("INACTIVE").size());
            userStats.put("adminUsers", userService.getUsersByRole("ADMIN").size());
            userStats.put("normalUsers", userService.getUsersByRole("USER").size());
            
            response.put("success", true);
            response.put("userStatistics", userStats);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取用户统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取演唱会统计信息")
    @GetMapping("/concerts")
    public ResponseEntity<Map<String, Object>> getConcertStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> concertStats = new HashMap<>();
            
            concertStats.put("totalConcerts", concertService.getConcertCount());
            concertStats.put("upcomingConcerts", concertService.getConcertsByStatus("UPCOMING").size());
            concertStats.put("ongoingConcerts", concertService.getConcertsByStatus("ONGOING").size());
            concertStats.put("finishedConcerts", concertService.getConcertsByStatus("FINISHED").size());
            concertStats.put("cancelledConcerts", concertService.getConcertsByStatus("CANCELLED").size());
            
            // 按类别统计
            concertStats.put("popConcerts", concertService.getConcertsByCategory("流行").size());
            concertStats.put("rockConcerts", concertService.getConcertsByCategory("摇滚").size());
            concertStats.put("classicalConcerts", concertService.getConcertsByCategory("古典").size());
            
            response.put("success", true);
            response.put("concertStatistics", concertStats);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取订单统计信息")
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> orderStats = orderService.getOrderStatistics();
            response.put("success", true);
            response.put("orderStatistics", orderStats);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取订单统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取公告统计信息")
    @GetMapping("/announcements")
    public ResponseEntity<Map<String, Object>> getAnnouncementStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> announcementStats = new HashMap<>();
            
            announcementStats.put("totalAnnouncements", announcementService.getAnnouncementCount());
            announcementStats.put("publishedAnnouncements", announcementService.getPublishedAnnouncements().size());
            announcementStats.put("draftAnnouncements", announcementService.getAnnouncementsByStatus("DRAFT").size());
            announcementStats.put("archivedAnnouncements", announcementService.getAnnouncementsByStatus("ARCHIVED").size());
            announcementStats.put("activeAnnouncements", announcementService.getActiveAnnouncements().size());
            
            // 按类型统计
            announcementStats.put("systemAnnouncements", announcementService.getAnnouncementsByType("SYSTEM").size());
            announcementStats.put("concertAnnouncements", announcementService.getAnnouncementsByType("CONCERT").size());
            announcementStats.put("maintenanceAnnouncements", announcementService.getAnnouncementsByType("MAINTENANCE").size());
            
            // 按优先级统计
            announcementStats.put("urgentAnnouncements", announcementService.getAnnouncementsByPriority("URGENT").size());
            announcementStats.put("highPriorityAnnouncements", announcementService.getAnnouncementsByPriority("HIGH").size());
            announcementStats.put("mediumPriorityAnnouncements", announcementService.getAnnouncementsByPriority("MEDIUM").size());
            announcementStats.put("lowPriorityAnnouncements", announcementService.getAnnouncementsByPriority("LOW").size());
            
            response.put("success", true);
            response.put("announcementStatistics", announcementStats);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取公告统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取收入统计信息
    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> getRevenueStatistics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> revenueStats = new HashMap<>();
            
            // 这里可以添加更复杂的收入统计逻辑
            // 例如：按时间段统计收入、按演唱会统计收入等
            
            // 获取已支付订单
            int paidOrders = orderService.getOrdersByPaymentStatus("PAID").size();
            revenueStats.put("paidOrders", paidOrders);
            
            // 获取退款订单
            int refundedOrders = orderService.getOrdersByPaymentStatus("REFUNDED").size();
            revenueStats.put("refundedOrders", refundedOrders);
            
            // 获取待支付订单
            int pendingOrders = orderService.getOrdersByPaymentStatus("PENDING").size();
            revenueStats.put("pendingOrders", pendingOrders);
            
            response.put("success", true);
            response.put("revenueStatistics", revenueStats);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取收入统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取热门演唱会统计
    @GetMapping("/popular-concerts")
    public ResponseEntity<Map<String, Object>> getPopularConcerts() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 这里可以添加热门演唱会统计逻辑
            // 例如：按订单数量排序的演唱会列表
            
            response.put("success", true);
            response.put("message", "热门演唱会统计功能待实现");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取热门演唱会统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取时间段统计
    @GetMapping("/time-range")
    public ResponseEntity<Map<String, Object>> getTimeRangeStatistics(
            @RequestParam String startTime,
            @RequestParam String endTime) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);
            
            Map<String, Object> timeRangeStats = new HashMap<>();
            
            // 统计时间段内的订单
            timeRangeStats.put("ordersInRange", orderService.getOrdersByTimeRange(start, end).size());
            
            // 统计时间段内的公告
            timeRangeStats.put("announcementsInRange", announcementService.getAnnouncementsByTimeRange(start, end).size());
            
            response.put("success", true);
            response.put("timeRangeStatistics", timeRangeStats);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取时间段统计失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
} 