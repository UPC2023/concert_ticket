package org.demo02.service.impl;

import org.demo02.dao.AnnouncementMapper;
import org.demo02.entity.Announcement;
import org.demo02.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    
    @Autowired
    private AnnouncementMapper announcementMapper;
    
    @Override
    public boolean addAnnouncement(Announcement announcement) {
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());
        return announcementMapper.insert(announcement) > 0;
    }
    
    @Override
    public boolean deleteAnnouncement(Long id) {
        return announcementMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        announcement.setUpdateTime(LocalDateTime.now());
        return announcementMapper.update(announcement) > 0;
    }
    
    @Override
    public Announcement getAnnouncementById(Long id) {
        return announcementMapper.findById(id);
    }
    
    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementMapper.findAll();
    }
    
    @Override
    public Map<String, Object> getAnnouncementsByPage(int currentPage, int size) {
        Map<String, Object> result = new HashMap<>();
        
        int offset = (currentPage - 1) * size;
        List<Announcement> announcements = announcementMapper.findByPage(offset, size);
        int total = announcementMapper.countTotal();
        
        result.put("announcements", announcements);
        result.put("total", total);
        result.put("currentPage", currentPage);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return result;
    }
    
    @Override
    public List<Announcement> getAnnouncementsByType(String type) {
        return announcementMapper.findByType(type);
    }
    
    @Override
    public List<Announcement> getAnnouncementsByPriority(String priority) {
        return announcementMapper.findByPriority(priority);
    }
    
    @Override
    public List<Announcement> getAnnouncementsByStatus(String status) {
        return announcementMapper.findByStatus(status);
    }
    
    @Override
    public List<Announcement> getAnnouncementsByTargetAudience(String targetAudience) {
        return announcementMapper.findByTargetAudience(targetAudience);
    }
    
    @Override
    public List<Announcement> getPublishedAnnouncements() {
        return announcementMapper.findPublished();
    }
    
    @Override
    public List<Announcement> getActiveAnnouncements() {
        return announcementMapper.findActive(LocalDateTime.now());
    }
    
    @Override
    public List<Announcement> getAnnouncementsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return announcementMapper.findByTimeRange(startTime, endTime);
    }
    
    @Override
    public List<Announcement> getAnnouncementsByAuthor(String author) {
        return announcementMapper.findByAuthor(author);
    }
    
    @Override
    public boolean publishAnnouncement(Long id) {
        Announcement announcement = new Announcement();
        announcement.setId(id);
        announcement.setStatus("PUBLISHED");
        announcement.setPublishTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());
        return announcementMapper.update(announcement) > 0;
    }
    
    @Override
    public boolean archiveAnnouncement(Long id) {
        return updateAnnouncementStatus(id, "ARCHIVED");
    }
    
    @Override
    public boolean updateAnnouncementStatus(Long id, String status) {
        return announcementMapper.updateStatus(id, status) > 0;
    }
    
    @Override
    public int getAnnouncementCount() {
        return announcementMapper.countTotal();
    }
    
    @Override
    public List<Announcement> getAnnouncementsByCondition(String title, String type, String priority, String status, String targetAudience) {
        return announcementMapper.findByCondition(title, type, priority, status, targetAudience);
    }

    @Override
    public Map<String, Object> getAnnouncementsByPageWithCondition(int currentPage, int size, String title, String type, String status, String priority, String author) {
        Map<String, Object> result = new HashMap<>();
        int offset = (currentPage - 1) * size;
        List<Announcement> list = announcementMapper.findByPageWithCondition(offset, size, title, type, status, priority, author);
        int total = announcementMapper.countByCondition(title, type, status, priority, author);
        result.put("announcements", list);
        result.put("total", total);
        result.put("currentPage", currentPage);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        return result;
    }
} 