package org.demo02.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo02.entity.Order;
import org.demo02.service.ConcertSeatService;
import org.demo02.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class OrderCleanupTask {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ConcertSeatService concertSeatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 1000)
    public void cleanExpiredOrders() {
        List<Order> pendingOrders = orderService.getPendingOrders();
        LocalDateTime now = LocalDateTime.now();
        for (Order order : pendingOrders) {
            if (order.getCreateTime() != null && order.getCreateTime().plusSeconds(30).isBefore(now)) {
                // 释放座位
                try {
                    String seatInfo = order.getSeatInfo();
                    if (seatInfo != null) {
                        List<Map<String, Object>> seatList = objectMapper.readValue(seatInfo, List.class);
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
                                concertSeatService.releaseSeat(concertId, seatId);
                            }
                        }
                    }
                } catch (Exception ignore) {}
                // 删除订单
                orderService.deleteOrder(order.getId());
            }
        }
    }
} 