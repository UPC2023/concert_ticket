package org.demo02.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "Order", description = "订单实体")
public class Order {
    @Schema(name = "id", description = "订单ID")
    private Long id;
    
    @Schema(name = "orderNo", description = "订单号")
    private String orderNo;
    
    @Schema(name = "userId", description = "用户ID")
    private Long userId;
    
    @Schema(name = "concertId", description = "演唱会ID")
    private Long concertId;
    
    @Schema(name = "quantity", description = "购票数量")
    private Integer quantity;
    
    @Schema(name = "unitPrice", description = "单价")
    private BigDecimal unitPrice;
    
    @Schema(name = "totalAmount", description = "总金额")
    private BigDecimal totalAmount;
    
    @Schema(name = "paymentMethod", description = "支付方式：ALIPAY-支付宝，WECHAT-微信，BANK-银行卡")
    private String paymentMethod;
    
    @Schema(name = "paymentStatus", description = "支付状态：PENDING-待支付，PAID-已支付，FAILED-支付失败，REFUNDED-已退款")
    private String paymentStatus;
    
    @Schema(name = "orderStatus", description = "订单状态：PENDING-待确认，CONFIRMED-已确认，CANCELLED-已取消，COMPLETED-已完成")
    private String orderStatus;
    
    @Schema(name = "ticketStatus", description = "票务状态：UNUSED-未使用，USED-已使用，EXPIRED-已过期")
    private String ticketStatus;
    
    @Schema(name = "qrCode", description = "入场二维码")
    private String qrCode;
    
    @Schema(name = "seatInfo", description = "座位信息")
    private String seatInfo;
    
    @Schema(name = "remark", description = "备注")
    private String remark;
    
    @Schema(name = "payTime", description = "支付时间")
    private LocalDateTime payTime;
    
    @Schema(name = "useTime", description = "使用时间")
    private LocalDateTime useTime;
    
    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

    private String concertTitle;
    public String getConcertTitle() { return concertTitle; }
    public void setConcertTitle(String concertTitle) { this.concertTitle = concertTitle; }

    // 构造函数
    public Order() {}

    public Order(Long userId, Long concertId, Integer quantity, BigDecimal unitPrice) {
        this.userId = userId;
        this.concertId = concertId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = unitPrice; // 直接用unitPrice作为总价
        this.orderNo = generateOrderNo();
        this.paymentStatus = "PENDING";
        this.orderStatus = "PENDING";
        this.ticketStatus = "UNUSED";
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    // 生成订单号
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getUseTime() {
        return useTime;
    }

    public void setUseTime(LocalDateTime useTime) {
        this.useTime = useTime;
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