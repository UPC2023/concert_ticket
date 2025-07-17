package org.demo02.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.demo02.entity.Announcement;
import org.demo02.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//允许跨域访问
@Tag(name = "公告管理")
@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    
    @Autowired
    private AnnouncementService announcementService;
    
    @Operation(summary = "添加公告")
    @PostMapping
    public ResponseEntity<Map<String, Object>> addAnnouncement(@RequestBody Announcement announcement) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = announcementService.addAnnouncement(announcement);
            if (success) {
                response.put("success", true);
                response.put("message", "公告添加成功");
            } else {
                response.put("success", false);
                response.put("message", "公告添加失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "公告添加失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取公告详情")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAnnouncementById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Announcement announcement = announcementService.getAnnouncementById(id);
            if (announcement != null) {
                response.put("success", true);
                response.put("announcement", announcement);
            } else {
                response.put("success", false);
                response.put("message", "公告不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取公告信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "更新公告信息")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            announcement.setId(id);
            boolean success = announcementService.updateAnnouncement(announcement);
            if (success) {
                response.put("success", true);
                response.put("message", "公告更新成功");
            } else {
                response.put("success", false);
                response.put("message", "公告更新失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "公告更新失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "删除公告")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAnnouncement(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = announcementService.deleteAnnouncement(id);
            if (success) {
                response.put("success", true);
                response.put("message", "公告删除成功");
            } else {
                response.put("success", false);
                response.put("message", "公告删除失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "公告删除失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取所有公告")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAnnouncementList() {
        Map<String, Object> res = new HashMap<>();
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        res.put("success", true);
        res.put("announcements", announcements);
        return ResponseEntity.ok(res);
    }
    
    @Operation(summary = "分页获取公告")
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getAnnouncementsByPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String author) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = announcementService.getAnnouncementsByPageWithCondition(
                currentPage, size, title, type, status, priority, author);
            response.put("success", true);
            response.putAll(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取公告列表失败：" + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取已发布公告")
    @GetMapping("/published")
    public ResponseEntity<Map<String, Object>> getPublishedAnnouncements() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Announcement> announcements = announcementService.getPublishedAnnouncements();
            response.put("success", true);
            response.put("announcements", announcements);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取已发布公告失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 获取活跃公告（未过期）
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveAnnouncements() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Announcement> announcements = announcementService.getActiveAnnouncements();
            response.put("success", true);
            response.put("announcements", announcements);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取活跃公告失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 根据类型获取公告
    @GetMapping("/type/{type}")
    public ResponseEntity<Map<String, Object>> getAnnouncementsByType(@PathVariable String type) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Announcement> announcements = announcementService.getAnnouncementsByType(type);
            response.put("success", true);
            response.put("announcements", announcements);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取公告列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 根据优先级获取公告
    @GetMapping("/priority/{priority}")
    public ResponseEntity<Map<String, Object>> getAnnouncementsByPriority(@PathVariable String priority) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Announcement> announcements = announcementService.getAnnouncementsByPriority(priority);
            response.put("success", true);
            response.put("announcements", announcements);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取公告列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 发布公告
    @PostMapping("/{id}/publish")
    public ResponseEntity<Map<String, Object>> publishAnnouncement(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = announcementService.publishAnnouncement(id);
            if (success) {
                response.put("success", true);
                response.put("message", "公告发布成功");
            } else {
                response.put("success", false);
                response.put("message", "公告发布失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "公告发布失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 归档公告
    @PostMapping("/{id}/archive")
    public ResponseEntity<Map<String, Object>> archiveAnnouncement(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = announcementService.archiveAnnouncement(id);
            if (success) {
                response.put("success", true);
                response.put("message", "公告归档成功");
            } else {
                response.put("success", false);
                response.put("message", "公告归档失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "公告归档失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 更新公告状态
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateAnnouncementStatus(@PathVariable Long id, @RequestBody Map<String, String> statusData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String status = statusData.get("status");
            boolean success = announcementService.updateAnnouncementStatus(id, status);
            if (success) {
                response.put("success", true);
                response.put("message", "状态更新成功");
            } else {
                response.put("success", false);
                response.put("message", "状态更新失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "状态更新失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
} 