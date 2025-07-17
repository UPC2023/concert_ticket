package org.demo02.controller;

import org.demo02.entity.User;
import org.demo02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = userService.register(user);
            if (success) {
                response.put("success", true);
                response.put("message", "注册成功");
            } else {
                response.put("success", false);
                response.put("message", "注册失败，用户可能已存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "注册失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String username = loginData.get("username");
            String password = loginData.get("password");
            
            User user = userService.login(username, password);
            if (user != null) {
                response.put("success", true);
                response.put("message", "登录成功");
                response.put("user", user);
            } else {
                response.put("success", false);
                response.put("message", "用户名或密码错误");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "登录失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                response.put("success", true);
                response.put("user", user);
            } else {
                response.put("success", false);
                response.put("message", "用户不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取用户信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            user.setId(id);
            boolean success = userService.updateUser(user);
            if (success) {
                response.put("success", true);
                response.put("message", "更新成功");
            } else {
                response.put("success", false);
                response.put("message", "更新失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "修改密码")
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwordData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String oldPassword = passwordData.get("oldPassword");
            String newPassword = passwordData.get("newPassword");
            
            boolean success = userService.changePassword(id, oldPassword, newPassword);
            if (success) {
                response.put("success", true);
                response.put("message", "密码修改成功");
            } else {
                response.put("success", false);
                response.put("message", "原密码错误或修改失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "密码修改失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取所有用户（管理员功能）")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<User> users = userService.getAllUsers();
            response.put("success", true);
            response.put("users", users);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取用户列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "分页获取用户（管理员功能）")
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getUsersByPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = userService.getUsersByPage(currentPage, size);
            response.put("success", true);
            response.putAll(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取用户列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "删除用户（管理员功能）")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = userService.deleteUser(id);
            if (success) {
                response.put("success", true);
                response.put("message", "删除成功");
            } else {
                response.put("success", false);
                response.put("message", "删除失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "更新用户状态（管理员功能）")
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, String> statusData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String status = statusData.get("status");
            boolean success = userService.updateUserStatus(id, status);
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