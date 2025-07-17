package org.demo02.service.impl;

import org.demo02.dao.UserMapper;
import org.demo02.entity.User;
import org.demo02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public boolean register(User user) {
        // 检查用户是否已存在
        if (isUserExists(user.getUsername(), user.getEmail(), user.getPhone())) {
            return false;
        }
        
        // 加密密码
        user.setPassword(encryptPassword(user.getPassword()));
        user.setRole("USER");
        user.setStatus("ACTIVE");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        return userMapper.insert(user) > 0;
    }
    
    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(encryptPassword(password))) {
            return user;
        }
        return null;
    }
    
    @Override
    public boolean addUser(User user) {
        // 检查用户是否已存在
        if (isUserExists(user.getUsername(), user.getEmail(), user.getPhone())) {
            return false;
        }
        
        // 加密密码
        user.setPassword(encryptPassword(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        return userMapper.insert(user) > 0;
    }
    
    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateUser(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user) > 0;
    }
    
    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }
    
    @Override
    public User getUserByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
    
    @Override
    public Map<String, Object> getUsersByPage(int currentPage, int size) {
        Map<String, Object> result = new HashMap<>();
        
        int offset = (currentPage - 1) * size;
        List<User> users = userMapper.findByPage(offset, size);
        int total = userMapper.countTotal();
        
        result.put("users", users);
        result.put("total", total);
        result.put("currentPage", currentPage);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return result;
    }
    
    @Override
    public List<User> getUsersByRole(String role) {
        return userMapper.findByRole(role);
    }
    
    @Override
    public List<User> getUsersByStatus(String status) {
        return userMapper.findByStatus(status);
    }
    
    @Override
    public boolean updateUserStatus(Long id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean updateUserRole(Long id, String role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean resetPassword(Long id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(encryptPassword(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user != null && user.getPassword().equals(encryptPassword(oldPassword))) {
            user.setPassword(encryptPassword(newPassword));
            user.setUpdateTime(LocalDateTime.now());
            return userMapper.update(user) > 0;
        }
        return false;
    }
    
    @Override
    public boolean isUserExists(String username, String email, String phone) {
        return userMapper.findByUsername(username) != null ||
               userMapper.findByEmail(email) != null ||
               userMapper.findByPhone(phone) != null;
    }
    
    @Override
    public int getUserCount() {
        return userMapper.countTotal();
    }
    
    @Override
    public List<User> getUsersByCondition(String username, String email, String phone, String role, String status) {
        return userMapper.findByCondition(username, email, phone, role, status);
    }
    
    // 密码加密方法
    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
} 