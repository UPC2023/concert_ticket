package org.demo02.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "Announcement", description = "公告实体")
public class Announcement {
    @Schema(name = "id", description = "公告ID")
    private Long id;
    
    @Schema(name = "title", description = "公告标题")
    private String title;
    
    @Schema(name = "content", description = "公告内容")
    private String content;
    
    @Schema(name = "type", description = "公告类型：SYSTEM-系统公告，CONCERT-演唱会相关，MAINTENANCE-维护通知")
    private String type;
    
    @Schema(name = "priority", description = "优先级：LOW-低，MEDIUM-中，HIGH-高，URGENT-紧急")
    private String priority;
    
    @Schema(name = "targetAudience", description = "目标受众：ALL-所有用户，USER-普通用户，ADMIN-管理员")
    private String targetAudience;
    
    @Schema(name = "status", description = "状态：DRAFT-草稿，PUBLISHED-已发布，ARCHIVED-已归档")
    private String status;
    
    @Schema(name = "publishTime", description = "发布时间")
    private LocalDateTime publishTime;
    
    @Schema(name = "expireTime", description = "过期时间")
    private LocalDateTime expireTime;
    
    @Schema(name = "author", description = "发布者")
    private String author;
    
    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

    // 构造函数
    public Announcement() {}

    public Announcement(String title, String content, String type, String priority) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.targetAudience = "ALL";
        this.status = "DRAFT";
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 