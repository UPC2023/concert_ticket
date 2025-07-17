# 演唱会购票系统

## 项目简介

这是一个基于Spring Boot + MyBatis的演唱会购票系统后端服务，提供完整的演唱会票务管理功能。

## 主要功能模块

### 1. 用户管理模块
- 用户注册、登录
- 用户信息管理
- 密码修改
- 用户状态管理（管理员功能）
- 用户角色管理（普通用户/管理员）

### 2. 演唱会信息模块
- 演唱会信息管理（增删改查）
- 演唱会分类管理
- 座位管理
- 演唱会状态管理
- 演唱会搜索和筛选

### 3. 支付与订单管理模块
- 订单创建和管理
- 支付处理（支持多种支付方式）
- 订单状态跟踪
- 退款处理
- 订单查询和统计

### 4. 订单核销与入场模块
- 二维码生成
- 票务核销
- 入场验证
- 票务状态管理

### 5. 公告与消息通知模块
- 系统公告管理
- 演唱会相关通知
- 维护通知
- 公告发布和归档

### 6. 数据统计与分析模块（管理员专用）
- 系统总体统计
- 用户统计
- 演唱会统计
- 订单统计
- 收入统计
- 公告统计

## 技术栈

- **后端框架**: Spring Boot 3.3.13
- **ORM框架**: MyBatis 3.0.4
- **数据库**: MySQL 8.0+
- **构建工具**: Maven
- **Java版本**: JDK 17

## 项目结构

```
src/main/java/org/demo02/
├── controller/          # 控制器层
│   ├── UserController.java
│   ├── ConcertController.java
│   ├── OrderController.java
│   ├── AnnouncementController.java
│   └── StatisticsController.java
├── service/            # 服务层
│   ├── UserService.java
│   ├── ConcertService.java
│   ├── OrderService.java
│   ├── AnnouncementService.java
│   └── impl/           # 服务实现
├── dao/               # 数据访问层
│   ├── UserMapper.java
│   ├── ConcertMapper.java
│   ├── OrderMapper.java
│   └── AnnouncementMapper.java
├── entity/            # 实体类
│   ├── User.java
│   ├── Concert.java
│   ├── Order.java
│   └── Announcement.java
└── Demo02Application.java

src/main/resources/
├── mappers/           # MyBatis映射文件
│   ├── UserMapper.xml
│   ├── ConcertMapper.xml
│   ├── OrderMapper.xml
│   └── AnnouncementMapper.xml
├── sql/              # 数据库脚本
│   └── init.sql
└── application.yml   # 配置文件
```

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置
1. 创建MySQL数据库
2. 执行 `src/main/resources/sql/init.sql` 脚本创建表结构和测试数据
3. 修改 `application.yml` 中的数据库连接信息

### 3. 运行项目
```bash
# 克隆项目
git clone [项目地址]

# 进入项目目录
cd demo02

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 4. 访问接口
项目启动后，可以通过以下地址访问：
- 基础URL: `http://localhost:8089/demo02`
- API文档: `http://localhost:8089/demo02/swagger-ui.html`

## API接口说明

### 用户相关接口
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `GET /api/user/{id}` - 获取用户信息
- `PUT /api/user/{id}` - 更新用户信息
- `POST /api/user/{id}/change-password` - 修改密码

### 演唱会相关接口
- `GET /api/concert/list` - 获取演唱会列表
- `GET /api/concert/{id}` - 获取演唱会详情
- `POST /api/concert` - 添加演唱会（管理员）
- `PUT /api/concert/{id}` - 更新演唱会信息（管理员）
- `DELETE /api/concert/{id}` - 删除演唱会（管理员）

### 订单相关接口
- `POST /api/order/create` - 创建订单
- `GET /api/order/{id}` - 获取订单详情
- `POST /api/order/{id}/pay` - 支付订单
- `POST /api/order/{id}/cancel` - 取消订单
- `POST /api/order/verify-ticket` - 核销票务

### 公告相关接口
- `GET /api/announcement/list` - 获取公告列表
- `GET /api/announcement/published` - 获取已发布公告
- `POST /api/announcement` - 添加公告（管理员）
- `PUT /api/announcement/{id}` - 更新公告（管理员）

### 统计相关接口（管理员专用）
- `GET /api/statistics/overview` - 系统总体统计
- `GET /api/statistics/users` - 用户统计
- `GET /api/statistics/concerts` - 演唱会统计
- `GET /api/statistics/orders` - 订单统计
- `GET /api/statistics/revenue` - 收入统计

## 测试账号

系统初始化后包含以下测试账号：

### 管理员账号
- 用户名: `admin`
- 密码: `123456`
- 角色: `ADMIN`

### 普通用户账号
- 用户名: `user1`
- 密码: `123456`
- 角色: `USER`

- 用户名: `user2`
- 密码: `123456`
- 角色: `USER`

## 数据库设计

### 用户表 (user)
- 用户基本信息
- 认证信息
- 角色和状态管理

### 演唱会表 (concert)
- 演唱会基本信息
- 座位管理
- 价格和状态

### 订单表 (order)
- 订单信息
- 支付状态
- 票务状态

### 公告表 (announcement)
- 公告内容
- 发布状态
- 目标受众

## 注意事项

1. 密码使用MD5加密存储
2. 订单号自动生成，格式为：`ORD` + 时间戳 + 随机数
3. 二维码自动生成，用于票务核销
4. 座位数量在创建订单时自动扣减
5. 支持订单取消和退款功能
6. 管理员可以查看所有统计数据

## 扩展功能

系统预留了以下扩展接口：
- 支付网关集成
- 短信通知
- 邮件通知
- 文件上传
- 权限管理
- 日志记录

## 联系方式

如有问题或建议，请联系开发团队。 