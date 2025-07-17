package org.demo02.service;

import org.demo02.entity.Concert;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ConcertService {
    // 添加演唱会
    boolean addConcert(Concert concert);
    
    // 删除演唱会
    boolean deleteConcert(Long id);
    
    // 更新演唱会信息
    boolean updateConcert(Concert concert);
    
    // 根据ID查询演唱会
    Concert getConcertById(Long id);
    
    // 查询所有演唱会
    List<Concert> getAllConcerts();
    
    // 分页查询演唱会
    Map<String, Object> getConcertsByPage(int currentPage, int size);
    
    // 带条件的分页查询演唱会
    Map<String, Object> getConcertsByPageWithCondition(int currentPage, int size, String title, String artist, String category, String status, String address, String year, String month);
    
    // 根据状态查询演唱会
    List<Concert> getConcertsByStatus(String status);
    
    // 根据类别查询演唱会
    List<Concert> getConcertsByCategory(String category);
    
    // 根据艺术家查询演唱会
    List<Concert> getConcertsByArtist(String artist);
    
    // 根据场馆查询演唱会
    List<Concert> getConcertsByVenue(String venue);
    
    // 查询即将开始的演唱会
    List<Concert> getUpcomingConcerts(LocalDateTime startTime, LocalDateTime endTime);
    
    // 根据时间范围查询演唱会
    List<Concert> getConcertsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    // 根据价格范围查询演唱会
    List<Concert> getConcertsByPriceRange(Double minPrice, Double maxPrice);
    
    // 更新演唱会状态
    boolean updateConcertStatus(Long id, String status);
    
    // 更新可用座位数
    boolean updateAvailableSeats(Long id, Integer availableSeats);
    
    // 减少可用座位数（购票时调用）
    boolean decreaseAvailableSeats(Long id, Integer quantity);
    
    // 增加可用座位数（退票时调用）
    boolean increaseAvailableSeats(Long id, Integer quantity);
    
    // 检查演唱会是否有足够座位
    boolean hasEnoughSeats(Long concertId, Integer quantity);
    
    // 统计演唱会总数
    int getConcertCount();
    
    // 根据条件查询演唱会
    List<Concert> getConcertsByCondition(String title, String artist, String venue, String category, String status);
} 