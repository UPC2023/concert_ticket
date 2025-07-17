package org.demo02.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "Concert", description = "演唱会信息实体")
public class Concert {
    @Schema(name = "id", description = "演唱会ID")
    private Long id;
    
    @Schema(name = "title", description = "演唱会标题")
    private String title;
    
    @Schema(name = "artist", description = "演出艺术家")
    private String artist;
    
    @Schema(name = "venue", description = "演出场馆")
    private String venue;
    
    @Schema(name = "venueAddress", description = "场馆地址")
    private String venueAddress;
    
    @Schema(name = "concertTime", description = "演出时间")
    private LocalDateTime concertTime;
    
    @Schema(name = "duration", description = "演出时长（分钟）")
    private Integer duration;
    
    @Schema(name = "description", description = "演出描述")
    private String description;
    
    @Schema(name = "posterUrl", description = "海报图片URL")
    private String posterUrl;
    
    @Schema(name = "totalSeats", description = "总座位数")
    private Integer totalSeats;
    
    @Schema(name = "availableSeats", description = "可用座位数")
    private Integer availableSeats;
    
    @Schema(name = "price", description = "票价")
    private BigDecimal price;
    
    @Schema(name = "category", description = "演出类别：POP-流行，ROCK-摇滚，CLASSICAL-古典等")
    private String category;
    
    @Schema(name = "status", description = "状态：UPCOMING-即将开始，ONGOING-进行中，FINISHED-已结束，CANCELLED-已取消")
    private String status;
    
    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

    // 构造函数
    public Concert() {}

    public Concert(String title, String artist, String venue, LocalDateTime concertTime, 
                   Integer totalSeats, BigDecimal price) {
        this.title = title;
        this.artist = artist;
        this.venue = venue;
        this.concertTime = concertTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.price = price;
        this.status = "UPCOMING";
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public LocalDateTime getConcertTime() {
        return concertTime;
    }

    public void setConcertTime(LocalDateTime concertTime) {
        this.concertTime = concertTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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