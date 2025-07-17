package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo02.entity.Announcement;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AnnouncementMapper {
    // 插入公告
    int insert(Announcement announcement);
    
    // 根据ID删除公告
    int deleteById(Long id);
    
    // 更新公告信息
    int update(Announcement announcement);
    
    // 根据ID查询公告
    Announcement findById(Long id);
    
    // 查询所有公告
    List<Announcement> findAll();
    
    // 分页查询公告
    List<Announcement> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    // 条件分页查询公告
    List<Announcement> findByPageWithCondition(@Param("offset") int offset,
                                               @Param("limit") int limit,
                                               @Param("title") String title,
                                               @Param("type") String type,
                                               @Param("status") String status,
                                               @Param("priority") String priority,
                                               @Param("author") String author);

    // 根据类型查询公告
    List<Announcement> findByType(String type);
    
    // 根据优先级查询公告
    List<Announcement> findByPriority(String priority);
    
    // 根据状态查询公告
    List<Announcement> findByStatus(String status);
    
    // 根据目标受众查询公告
    List<Announcement> findByTargetAudience(String targetAudience);
    
    // 查询已发布的公告
    List<Announcement> findPublished();
    
    // 查询未过期的公告
    List<Announcement> findActive(@Param("currentTime") LocalDateTime currentTime);
    
    // 根据时间范围查询公告
    List<Announcement> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                       @Param("endTime") LocalDateTime endTime);
    
    // 根据作者查询公告
    List<Announcement> findByAuthor(String author);
    
    // 更新公告状态
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    // 统计公告总数
    int countTotal();
    
    // 条件统计公告总数
    int countByCondition(@Param("title") String title,
                        @Param("type") String type,
                        @Param("status") String status,
                        @Param("priority") String priority,
                        @Param("author") String author);
    
    // 根据条件查询公告
    List<Announcement> findByCondition(@Param("title") String title, 
                                       @Param("type") String type, 
                                       @Param("priority") String priority, 
                                       @Param("status") String status, 
                                       @Param("targetAudience") String targetAudience);
} 