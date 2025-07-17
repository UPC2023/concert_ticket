package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo02.entity.Concert;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ConcertMapper {
    // 插入演唱会
    int insert(Concert concert);
    
    // 根据ID删除演唱会
    int deleteById(Long id);
    
    // 更新演唱会信息
    int update(Concert concert);
    
    // 根据ID查询演唱会
    Concert findById(Long id);
    
    // 查询所有演唱会
    List<Concert> findAll();
    
    // 分页查询演唱会
    List<Concert> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    // 带条件的分页查询演唱会
    List<Concert> findByPageWithCondition(@Param("offset") int offset, 
                                         @Param("limit") int limit,
                                         @Param("title") String title,
                                         @Param("artist") String artist,
                                         @Param("category") String category,
                                         @Param("status") String status,
                                         @Param("address") String address,
                                         @Param("year") String year,
                                         @Param("month") String month);
    
    // 根据状态查询演唱会
    List<Concert> findByStatus(String status);
    
    // 根据类别查询演唱会
    List<Concert> findByCategory(String category);
    
    // 根据艺术家查询演唱会
    List<Concert> findByArtist(String artist);
    
    // 根据场馆查询演唱会
    List<Concert> findByVenue(String venue);
    
    // 查询即将开始的演唱会
    List<Concert> findUpcoming(@Param("startTime") LocalDateTime startTime, 
                               @Param("endTime") LocalDateTime endTime);
    
    // 根据时间范围查询演唱会
    List<Concert> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                  @Param("endTime") LocalDateTime endTime);
    
    // 根据价格范围查询演唱会
    List<Concert> findByPriceRange(@Param("minPrice") Double minPrice, 
                                   @Param("maxPrice") Double maxPrice);
    
    // 更新可用座位数
    int updateAvailableSeats(@Param("id") Long id, @Param("availableSeats") Integer availableSeats);
    
    // 统计演唱会总数
    int countTotal();
    
    // 根据条件统计演唱会总数
    int countByCondition(@Param("title") String title,
                        @Param("artist") String artist,
                        @Param("category") String category,
                        @Param("status") String status,
                        @Param("address") String address,
                        @Param("year") String year,
                        @Param("month") String month);
    
    // 根据条件查询演唱会
    List<Concert> findByCondition(@Param("title") String title, 
                                  @Param("artist") String artist, 
                                  @Param("venue") String venue, 
                                  @Param("category") String category, 
                                  @Param("status") String status);
} 