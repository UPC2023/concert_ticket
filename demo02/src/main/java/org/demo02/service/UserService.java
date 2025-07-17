package org.demo02.service;

import org.demo02.entity.User;
import java.util.List;
import java.util.Map;

public interface UserService {
    // 用户注册
    boolean register(User user);
    
    // 用户登录
    User login(String username, String password);
    
    // 添加用户
    boolean addUser(User user);
    
    // 删除用户
    boolean deleteUser(Long id);
    
    // 更新用户信息
    boolean updateUser(User user);
    
    // 根据ID查询用户
    User getUserById(Long id);
    
    // 根据用户名查询用户
    User getUserByUsername(String username);
    
    // 根据邮箱查询用户
    User getUserByEmail(String email);
    
    // 根据手机号查询用户
    User getUserByPhone(String phone);
    
    // 查询所有用户
    List<User> getAllUsers();
    
    // 分页查询用户
    Map<String, Object> getUsersByPage(int currentPage, int size);
    
    // 根据角色查询用户
    List<User> getUsersByRole(String role);
    
    // 根据状态查询用户
    List<User> getUsersByStatus(String status);
    
    // 修改用户状态
    boolean updateUserStatus(Long id, String status);
    
    // 修改用户角色
    boolean updateUserRole(Long id, String role);
    
    // 重置用户密码
    boolean resetPassword(Long id, String newPassword);
    
    // 修改密码
    boolean changePassword(Long userId, String oldPassword, String newPassword);
    
    // 验证用户是否存在
    boolean isUserExists(String username, String email, String phone);
    
    // 统计用户总数
    int getUserCount();
    
    // 根据条件查询用户
    List<User> getUsersByCondition(String username, String email, String phone, String role, String status);
} 