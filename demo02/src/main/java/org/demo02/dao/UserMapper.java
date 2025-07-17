package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo02.entity.User;
import java.util.List;

@Mapper
public interface UserMapper {
    // 插入用户
    int insert(User user);
    
    // 根据ID删除用户
    int deleteById(Long id);
    
    // 更新用户信息
    int update(User user);
    
    // 根据ID查询用户
    User findById(Long id);
    
    // 根据用户名查询用户
    User findByUsername(String username);
    
    // 根据邮箱查询用户
    User findByEmail(String email);
    
    // 根据手机号查询用户
    User findByPhone(String phone);
    
    // 查询所有用户
    List<User> findAll();
    
    // 分页查询用户
    List<User> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    // 根据角色查询用户
    List<User> findByRole(String role);
    
    // 根据状态查询用户
    List<User> findByStatus(String status);
    
    // 统计用户总数
    int countTotal();
    
    // 根据条件查询用户
    List<User> findByCondition(@Param("username") String username, 
                              @Param("email") String email, 
                              @Param("phone") String phone, 
                              @Param("role") String role, 
                              @Param("status") String status);
} 