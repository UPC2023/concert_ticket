package org.demo02.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "User", description = "用户实体")
public class User {
    @Schema(name = "id", description = "用户ID")
    private Long id;
    
    @Schema(name = "username", description = "用户名")
    private String username;
    
    @Schema(name = "password", description = "密码")
    private String password;
    
    @Schema(name = "email", description = "邮箱")
    private String email;
    
    @Schema(name = "phone", description = "手机号")
    private String phone;
    
    @Schema(name = "realName", description = "真实姓名")
    private String realName;
    
    @Schema(name = "idCard", description = "身份证号")
    private String idCard;
    
    @Schema(name = "role", description = "用户角色：USER-普通用户，ADMIN-管理员")
    private String role;
    
    @Schema(name = "status", description = "用户状态：ACTIVE-正常，INACTIVE-禁用")
    private String status;
    
    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

    // 构造函数
    public User() {}

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = "USER";
        this.status = "ACTIVE";
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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