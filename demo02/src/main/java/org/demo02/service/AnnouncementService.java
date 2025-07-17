package org.demo02.service;

import org.demo02.entity.Announcement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AnnouncementService {
    // 添加公告
    boolean addAnnouncement(Announcement announcement);
    
    // 删除公告
    boolean deleteAnnouncement(Long id);
    
    // 更新公告信息
    boolean updateAnnouncement(Announcement announcement);
    
    // 根据ID查询公告
    Announcement getAnnouncementById(Long id);
    
    // 查询所有公告
    List<Announcement> getAllAnnouncements();
    
    // 分页查询公告
    Map<String, Object> getAnnouncementsByPage(int currentPage, int size);
    
    // 根据类型查询公告
    List<Announcement> getAnnouncementsByType(String type);
    
    // 根据优先级查询公告
    List<Announcement> getAnnouncementsByPriority(String priority);
    
    // 根据状态查询公告
    List<Announcement> getAnnouncementsByStatus(String status);
    
    // 根据目标受众查询公告
    List<Announcement> getAnnouncementsByTargetAudience(String targetAudience);
    
    // 查询已发布的公告
    List<Announcement> getPublishedAnnouncements();
    
    // 查询未过期的公告
    List<Announcement> getActiveAnnouncements();
    
    // 根据时间范围查询公告
    List<Announcement> getAnnouncementsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    // 根据作者查询公告
    List<Announcement> getAnnouncementsByAuthor(String author);
    
    // 发布公告
    boolean publishAnnouncement(Long id);
    
    // 归档公告
    boolean archiveAnnouncement(Long id);
    
    // 更新公告状态
    boolean updateAnnouncementStatus(Long id, String status);
    
    // 统计公告总数
    int getAnnouncementCount();
    
    // 根据条件查询公告
    List<Announcement> getAnnouncementsByCondition(String title, String type, String priority, String status, String targetAudience);
    
    // 带条件分页查询公告
    Map<String, Object> getAnnouncementsByPageWithCondition(int currentPage, int size, String title, String type, String status, String priority, String author);
} 