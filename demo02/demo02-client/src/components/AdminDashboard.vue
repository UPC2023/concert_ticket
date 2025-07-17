<template>
  <div class="admin-layout">
    <!-- 侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-title">管理后台</div>
      <el-menu :default-active="activeMenu" class="sidebar-menu" @select="handleMenuSelect">
        <el-menu-item index="dashboard"><i class="el-icon-data-analysis"></i> 数据概览</el-menu-item>
        <el-menu-item index="analysis"><i class="el-icon-pie-chart"></i> 数据分析</el-menu-item>
        <el-menu-item index="concert"><i class="el-icon-microphone"></i> 演唱会管理</el-menu-item>
        <el-menu-item index="order"><i class="el-icon-tickets"></i> 订单管理</el-menu-item>
        <el-menu-item index="user"><i class="el-icon-user"></i> 用户管理</el-menu-item>
        <el-menu-item index="announcement"><i class="el-icon-message"></i> 公告管理</el-menu-item>
      </el-menu>
    </aside>
    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 顶部栏 -->
      <header class="admin-header">
        <div class="header-left">
          <span class="header-title">{{ getHeaderTitle() }}</span>
        </div>
        <div class="header-right">
          <el-link type="primary" @click="goHome">返回首页</el-link>
          <el-divider direction="vertical"></el-divider>
          <el-avatar icon="el-icon-user-solid" size="small" />
          <span class="admin-username">admin <span class="admin-role">系统管理员</span></span>
        </div>
      </header>
      
      <!-- 数据概览内容 -->
      <div v-if="activeMenu === 'dashboard'" class="dashboard-content">
        <!-- 数据概览卡片 -->
        <div class="overview-section">
          <el-row :gutter="24">
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="overview-card">
                <div class="overview-icon purple"><i class="el-icon-money"></i></div>
                <div class="overview-title">￥0</div>
                <div class="overview-label">总收入</div>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="overview-card">
                <div class="overview-icon pink"><i class="el-icon-shopping-cart-full"></i></div>
                <div class="overview-title">0</div>
                <div class="overview-label">总订单</div>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="overview-card">
                <div class="overview-icon blue"><i class="el-icon-user"></i></div>
                <div class="overview-title">0</div>
                <div class="overview-label">总用户</div>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="overview-card">
                <div class="overview-icon green"><i class="el-icon-notebook-2"></i></div>
                <div class="overview-title">0</div>
                <div class="overview-label">活跃演唱会</div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        <!-- 今日数据 -->
        <div class="today-section">
          <el-row :gutter="24">
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="today-card">
                <div class="today-title">￥0</div>
                <div class="today-label">今日收入</div>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="today-card">
                <div class="today-title">0</div>
                <div class="today-label">今日订单</div>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="today-card">
                <div class="today-title">0</div>
                <div class="today-label">今日新用户</div>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="6">
              <el-card class="today-card">
                <div class="today-title">0</div>
                <div class="today-label">在线用户</div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        <!-- 快捷操作和最近活动 -->
        <div class="quick-section">
          <el-row :gutter="24">
            <el-col :xs="24" :sm="12" :md="16">
              <el-card class="quick-card">
                <div class="quick-title">快捷操作</div>
                <el-row :gutter="12">
                  <el-col :span="12">
                    <el-link type="primary" icon="el-icon-data-analysis">销售数据统计</el-link>
                    <div class="quick-desc">查看详细的销售数据和收入分析</div>
                  </el-col>
                  <el-col :span="12">
                    <el-link type="success" icon="el-icon-pie-chart">用户行为分析</el-link>
                    <div class="quick-desc">分析用户行为模式和偏好</div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8">
              <el-card class="quick-card">
                <div class="quick-title">最近活动</div>
                <ul class="activity-list">
                  <li><i class="el-icon-tickets"></i> 新订单 用户 张三 购买了周杰伦演唱会门票 <span class="activity-time">2分钟前</span></li>
                  <li><i class="el-icon-user"></i> 新用户注册 用户 李四 完成注册 <span class="activity-time">5分钟前</span></li>
                </ul>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 演唱会管理内容 -->
      <div v-if="activeMenu === 'concert'" class="concert-content">
        <!-- 搜索栏 -->
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="演唱会名称">
            <el-input v-model="searchForm.title" placeholder="请输入演唱会名称" clearable />
          </el-form-item>
          <el-form-item label="艺人">
            <el-input v-model="searchForm.artist" placeholder="请输入艺人名称" clearable />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="即将开始" value="UPCOMING" />
              <el-option label="进行中" value="ONGOING" />
              <el-option label="已结束" value="FINISHED" />
              <el-option label="已取消" value="CANCELLED" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSearch">搜索</el-button>
            <el-button @click="onReset">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="showAddDialog" plain>+ 添加演唱会</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="concerts" border stripe class="concert-table" v-loading="loading">
          <el-table-column prop="id" label="ID" width="60" align="center" />
          <el-table-column prop="title" label="演唱会名称" min-width="140" />
          <el-table-column prop="artist" label="艺人" min-width="100" />
          <el-table-column prop="venue" label="场馆" min-width="120" />
          <el-table-column prop="concertTime" label="演出时间" min-width="160">
            <template #default="scope">{{ formatTime(scope.row.concertTime) }}</template>
          </el-table-column>
          <el-table-column label="价格区间" min-width="100">
            <template #default="scope">
              {{ formatPriceRange(scope.row.price) }}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" min-width="80">
            <template #default="scope">
              <el-tag v-if="scope.row.status === 'UPCOMING'" type="primary">即将开始</el-tag>
              <el-tag v-else-if="scope.row.status === 'ONGOING'" type="success">进行中</el-tag>
              <el-tag v-else-if="scope.row.status === 'FINISHED'" type="info">已结束</el-tag>
              <el-tag v-else-if="scope.row.status === 'CANCELLED'" type="danger">已取消</el-tag>
              <el-tag v-else type="info">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="160">
            <template #default="scope">{{ formatTime(scope.row.createTime) }}</template>
          </el-table-column>
          <el-table-column label="操作" min-width="120">
            <template #default="scope">
              <el-link type="primary" @click="onView(scope.row)"><i class="el-icon-view"></i> 查看</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="info" @click="onEdit(scope.row)"><i class="el-icon-edit"></i> 编辑</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="danger" @click="onDelete(scope.row)"><i class="el-icon-delete"></i> 删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="pagination-bar">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            :page-size="pageSize"
            :current-page="currentPage"
            @size-change="handleSizeChange"
            @current-change="handlePageChange"
            :page-sizes="[10, 20, 50]"
          />
        </div>

        <!-- 添加/编辑演唱会对话框 -->
        <el-dialog 
          :title="dialogTitle" 
          v-model="dialogVisible" 
          width="600px"
          :before-close="handleDialogClose"
        >
          <el-form :model="concertForm" :rules="concertRules" ref="concertFormRef" label-width="100px">
            <el-form-item label="演唱会名称" prop="title">
              <el-input v-model="concertForm.title" placeholder="请输入演唱会名称" />
            </el-form-item>
            <el-form-item label="艺人" prop="artist">
              <el-input v-model="concertForm.artist" placeholder="请输入艺人名称" />
            </el-form-item>
            <el-form-item label="场馆" prop="venue">
              <el-input v-model="concertForm.venue" placeholder="请输入场馆名称" />
            </el-form-item>
            <el-form-item label="场馆地址" prop="venueAddress">
              <el-input v-model="concertForm.venueAddress" placeholder="请输入场馆地址" />
            </el-form-item>
            <el-form-item label="演出时间" prop="concertTime">
              <el-date-picker
                v-model="concertForm.concertTime"
                type="datetime"
                placeholder="选择演出时间"
                format="YYYY-MM-DD HH:mm:ss"
                value-format="YYYY-MM-DD HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
            <el-form-item label="演出时长" prop="duration">
              <el-input-number v-model="concertForm.duration" :min="30" :max="300" placeholder="分钟" />
            </el-form-item>
            <el-form-item label="票价" prop="price">
              <el-input-number v-model="concertForm.price" :min="0" :precision="2" placeholder="元" />
            </el-form-item>
            <el-form-item label="总座位数" prop="totalSeats">
              <el-input-number v-model="concertForm.totalSeats" :min="1" placeholder="座位数" />
            </el-form-item>
            <el-form-item label="演出类别" prop="category">
              <el-select v-model="concertForm.category" placeholder="请选择演出类别" style="width: 100%">
                <el-option label="流行" value="流行" />
                <el-option label="摇滚" value="摇滚" />
                <el-option label="古典" value="古典" />
                <el-option label="动漫" value="动漫" />
                <el-option label="爵士" value="爵士" />
                <el-option label="民谣" value="民谣" />
                <el-option label="电子" value="电子" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="concertForm.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="即将开始" value="UPCOMING" />
                <el-option label="进行中" value="ONGOING" />
                <el-option label="已结束" value="FINISHED" />
                <el-option label="已取消" value="CANCELLED" />
              </el-select>
            </el-form-item>
            <el-form-item label="海报URL" prop="posterUrl">
              <el-input v-model="concertForm.posterUrl" placeholder="请输入海报图片URL" />
            </el-form-item>
            <el-form-item label="演出描述" prop="description">
              <el-input 
                v-model="concertForm.description" 
                type="textarea" 
                :rows="3"
                placeholder="请输入演出描述"
              />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="submitForm" :loading="submitLoading">确定</el-button>
            </span>
          </template>
        </el-dialog>

        <!-- 查看演唱会详情对话框 -->
        <el-dialog 
          title="演唱会详情" 
          v-model="viewDialogVisible" 
          width="600px"
        >
          <div v-if="viewConcert" class="concert-detail">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="演唱会名称">{{ viewConcert.title }}</el-descriptions-item>
              <el-descriptions-item label="艺人">{{ viewConcert.artist }}</el-descriptions-item>
              <el-descriptions-item label="场馆">{{ viewConcert.venue }}</el-descriptions-item>
              <el-descriptions-item label="场馆地址">{{ viewConcert.venueAddress }}</el-descriptions-item>
              <el-descriptions-item label="演出时间">{{ formatTime(viewConcert.concertTime) }}</el-descriptions-item>
              <el-descriptions-item label="演出时长">{{ viewConcert.duration }}分钟</el-descriptions-item>
              <el-descriptions-item label="票价">¥{{ viewConcert.price }}</el-descriptions-item>
              <el-descriptions-item label="总座位数">{{ viewConcert.totalSeats }}</el-descriptions-item>
              <el-descriptions-item label="可用座位数">{{ viewConcert.availableSeats }}</el-descriptions-item>
              <el-descriptions-item label="演出类别">{{ getCategoryName(viewConcert.category) }}</el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag v-if="viewConcert.status === 'UPCOMING'" type="primary">即将开始</el-tag>
                <el-tag v-else-if="viewConcert.status === 'ONGOING'" type="success">进行中</el-tag>
                <el-tag v-else-if="viewConcert.status === 'FINISHED'" type="info">已结束</el-tag>
                <el-tag v-else-if="viewConcert.status === 'CANCELLED'" type="danger">已取消</el-tag>
                <el-tag v-else type="info">{{ viewConcert.status }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="创建时间">{{ formatTime(viewConcert.createTime) }}</el-descriptions-item>
              <el-descriptions-item label="更新时间">{{ formatTime(viewConcert.updateTime) }}</el-descriptions-item>
              <el-descriptions-item label="演出描述" :span="2">{{ viewConcert.description || '暂无描述' }}</el-descriptions-item>
            </el-descriptions>
            <div v-if="viewConcert.posterUrl" class="poster-preview">
              <h4>海报预览：</h4>
              <img :src="viewConcert.posterUrl" alt="海报" style="max-width: 100%; max-height: 200px;" />
            </div>
          </div>
        </el-dialog>
      </div>

      <!-- 订单管理内容 -->
      <div v-if="activeMenu === 'order'" class="order-content">
        <!-- 搜索栏 -->
        <el-form :inline="true" :model="orderSearchForm" class="search-form">
          <el-form-item label="订单号">
            <el-input v-model="orderSearchForm.orderNo" placeholder="请输入订单号" clearable />
          </el-form-item>
          <el-form-item label="用户ID">
            <el-input v-model="orderSearchForm.userId" placeholder="请输入用户ID" clearable />
          </el-form-item>
          <el-form-item label="支付状态">
            <el-select v-model="orderSearchForm.paymentStatus" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="待支付" value="PENDING" />
              <el-option label="已支付" value="PAID" />
              <el-option label="支付失败" value="FAILED" />
              <el-option label="已退款" value="REFUNDED" />
            </el-select>
          </el-form-item>
          <el-form-item label="订单状态">
            <el-select v-model="orderSearchForm.orderStatus" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="待确认" value="PENDING" />
              <el-option label="已确认" value="CONFIRMED" />
              <el-option label="已取消" value="CANCELLED" />
              <el-option label="已完成" value="COMPLETED" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onOrderSearch">搜索</el-button>
            <el-button @click="onOrderReset">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="orders" border stripe class="order-table" v-loading="orderLoading">
          <el-table-column prop="id" label="ID" width="60" align="center" />
          <el-table-column prop="orderNo" label="订单号" min-width="140" />
          <el-table-column prop="concertTitle" label="演唱会名称" min-width="140" />
          <el-table-column prop="userId" label="用户ID" min-width="80" align="center" />
          <el-table-column prop="quantity" label="数量" min-width="60" align="center" />
          <el-table-column prop="unitPrice" label="单价" min-width="80" align="center">
            <template #default="scope">¥{{ scope.row.unitPrice }}</template>
          </el-table-column>
          <el-table-column prop="totalAmount" label="总金额" min-width="100" align="center">
            <template #default="scope">¥{{ scope.row.totalAmount }}</template>
          </el-table-column>
          <el-table-column prop="paymentStatus" label="支付状态" min-width="100">
            <template #default="scope">
              <el-tag v-if="scope.row.paymentStatus === 'PENDING'" type="warning">待支付</el-tag>
              <el-tag v-else-if="scope.row.paymentStatus === 'PAID'" type="success">已支付</el-tag>
              <el-tag v-else-if="scope.row.paymentStatus === 'FAILED'" type="danger">支付失败</el-tag>
              <el-tag v-else-if="scope.row.paymentStatus === 'REFUNDED'" type="info">已退款</el-tag>
              <el-tag v-else type="info">{{ scope.row.paymentStatus }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="orderStatus" label="订单状态" min-width="100">
            <template #default="scope">
              <el-tag v-if="scope.row.orderStatus === 'PENDING'" type="warning">待确认</el-tag>
              <el-tag v-else-if="scope.row.orderStatus === 'CONFIRMED'" type="primary">已确认</el-tag>
              <el-tag v-else-if="scope.row.orderStatus === 'CANCELLED'" type="danger">已取消</el-tag>
              <el-tag v-else-if="scope.row.orderStatus === 'COMPLETED'" type="success">已完成</el-tag>
              <el-tag v-else type="info">{{ scope.row.orderStatus }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="160">
            <template #default="scope">{{ formatTime(scope.row.createTime) }}</template>
          </el-table-column>
          <el-table-column label="操作" min-width="150">
            <template #default="scope">
              <el-link type="primary" @click="onOrderView(scope.row)"><i class="el-icon-view"></i> 查看</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="success" @click="onOrderPay(scope.row)" v-if="scope.row.paymentStatus === 'PENDING'"><i class="el-icon-money"></i> 支付</el-link>
              <el-divider direction="vertical" v-if="scope.row.paymentStatus === 'PENDING'"></el-divider>
              <el-link type="warning" @click="onOrderCancel(scope.row)" v-if="scope.row.orderStatus === 'PENDING'"><i class="el-icon-close"></i> 取消</el-link>
              <el-divider direction="vertical" v-if="scope.row.orderStatus === 'PENDING'"></el-divider>
              <el-link type="danger" @click="onOrderDelete(scope.row)"><i class="el-icon-delete"></i> 删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="pagination-bar">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="orderTotal"
            :page-size="orderPageSize"
            :current-page="orderCurrentPage"
            @size-change="handleOrderSizeChange"
            @current-change="handleOrderPageChange"
            :page-sizes="[10, 20, 50]"
          />
        </div>

        <!-- 查看订单详情对话框 -->
        <el-dialog 
          title="订单详情" 
          v-model="orderViewDialogVisible" 
          width="700px"
        >
          <div v-if="viewOrder" class="order-detail">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="订单号">{{ viewOrder.orderNo }}</el-descriptions-item>
              <el-descriptions-item label="演唱会名称">{{ viewOrder.concertTitle }}</el-descriptions-item>
              <el-descriptions-item label="用户ID">{{ viewOrder.userId }}</el-descriptions-item>
              <el-descriptions-item label="购票数量">{{ viewOrder.quantity }}</el-descriptions-item>
              <el-descriptions-item label="单价">¥{{ viewOrder.unitPrice }}</el-descriptions-item>
              <el-descriptions-item label="总金额">¥{{ viewOrder.totalAmount }}</el-descriptions-item>
              <el-descriptions-item label="支付方式">{{ getPaymentMethodName(viewOrder.paymentMethod) }}</el-descriptions-item>
              <el-descriptions-item label="支付状态">
                <el-tag v-if="viewOrder.paymentStatus === 'PENDING'" type="warning">待支付</el-tag>
                <el-tag v-else-if="viewOrder.paymentStatus === 'PAID'" type="success">已支付</el-tag>
                <el-tag v-else-if="viewOrder.paymentStatus === 'FAILED'" type="danger">支付失败</el-tag>
                <el-tag v-else-if="viewOrder.paymentStatus === 'REFUNDED'" type="info">已退款</el-tag>
                <el-tag v-else type="info">{{ viewOrder.paymentStatus }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="订单状态">
                <el-tag v-if="viewOrder.orderStatus === 'PENDING'" type="warning">待确认</el-tag>
                <el-tag v-else-if="viewOrder.orderStatus === 'CONFIRMED'" type="primary">已确认</el-tag>
                <el-tag v-else-if="viewOrder.orderStatus === 'CANCELLED'" type="danger">已取消</el-tag>
                <el-tag v-else-if="viewOrder.orderStatus === 'COMPLETED'" type="success">已完成</el-tag>
                <el-tag v-else type="info">{{ viewOrder.orderStatus }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="票务状态">
                <el-tag v-if="viewOrder.ticketStatus === 'UNUSED'" type="primary">未使用</el-tag>
                <el-tag v-else-if="viewOrder.ticketStatus === 'USED'" type="success">已使用</el-tag>
                <el-tag v-else-if="viewOrder.ticketStatus === 'EXPIRED'" type="danger">已过期</el-tag>
                <el-tag v-else type="info">{{ viewOrder.ticketStatus }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="座位信息">{{ viewOrder.seatInfo || '暂无' }}</el-descriptions-item>
              <el-descriptions-item label="支付时间">{{ viewOrder.payTime ? formatTime(viewOrder.payTime) : '暂无' }}</el-descriptions-item>
              <el-descriptions-item label="使用时间">{{ viewOrder.useTime ? formatTime(viewOrder.useTime) : '暂无' }}</el-descriptions-item>
              <el-descriptions-item label="创建时间">{{ formatTime(viewOrder.createTime) }}</el-descriptions-item>
              <el-descriptions-item label="更新时间">{{ formatTime(viewOrder.updateTime) }}</el-descriptions-item>
              <el-descriptions-item label="备注" :span="2">{{ viewOrder.remark || '暂无' }}</el-descriptions-item>
            </el-descriptions>
            <div v-if="viewOrder.qrCode" class="qr-preview">
              <h4>入场二维码：</h4>
              <div class="qr-code">{{ viewOrder.qrCode }}</div>
            </div>
          </div>
        </el-dialog>

        <!-- 支付订单对话框 -->
        <el-dialog 
          title="支付订单" 
          v-model="orderPayDialogVisible" 
          width="400px"
        >
          <el-form :model="orderPayForm" label-width="100px">
            <el-form-item label="订单号">
              <span>{{ orderPayForm.orderNo }}</span>
            </el-form-item>
            <el-form-item label="支付金额">
              <span>¥{{ orderPayForm.totalAmount }}</span>
            </el-form-item>
            <el-form-item label="支付方式" prop="paymentMethod">
              <el-select v-model="orderPayForm.paymentMethod" placeholder="请选择支付方式" style="width: 100%">
                <el-option label="支付宝" value="ALIPAY" />
                <el-option label="微信支付" value="WECHAT" />
                <el-option label="银行卡" value="BANK" />
              </el-select>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="orderPayDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="submitOrderPay" :loading="orderPayLoading">确认支付</el-button>
            </span>
          </template>
        </el-dialog>
      </div>

      <!-- 用户管理内容 -->
      <div v-if="activeMenu === 'user'" class="user-content">
        <!-- 搜索栏 -->
        <el-form :inline="true" :model="userSearchForm" class="search-form">
          <el-form-item label="用户名">
            <el-input v-model="userSearchForm.username" placeholder="请输入用户名" clearable />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="userSearchForm.phone" placeholder="请输入手机号" clearable />
          </el-form-item>
          <el-form-item label="角色">
            <el-select v-model="userSearchForm.role" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="普通用户" value="USER" />
              <el-option label="管理员" value="ADMIN" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="userSearchForm.status" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="正常" value="ACTIVE" />
              <el-option label="禁用" value="NATIVE" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onUserSearch">搜索</el-button>
            <el-button @click="onUserReset">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="showUserAddDialog" plain>+ 添加用户</el-button>
          </el-form-item>
        </el-form>
        <!-- 用户表格 -->
        <el-table :data="users" border stripe class="user-table" v-loading="userLoading">
          <el-table-column prop="id" label="ID" width="60" align="center" />
          <el-table-column prop="username" label="用户名" min-width="120" />
          <el-table-column prop="email" label="邮箱" min-width="160" />
          <el-table-column prop="phone" label="手机号" min-width="120" />
          <el-table-column prop="role" label="角色" min-width="80">
            <template #default="scope">
              <el-tag v-if="scope.row.role === 'ADMIN'" type="danger">管理员</el-tag>
              <el-tag v-else type="info">普通用户</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" min-width="80">
            <template #default="scope">
              <el-tag v-if="scope.row.status === 'ACTIVE'" type="success">正常</el-tag>
              <el-tag v-else type="info">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="160">
            <template #default="scope">{{ formatTime(scope.row.createTime) }}</template>
          </el-table-column>
          <el-table-column label="操作" min-width="160">
            <template #default="scope">
              <el-link type="primary" @click="onUserView(scope.row)"><i class="el-icon-view"></i> 查看</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="info" @click="onUserEdit(scope.row)"><i class="el-icon-edit"></i> 编辑</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="danger" @click="onUserDelete(scope.row)"><i class="el-icon-delete"></i> 删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="pagination-bar">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="userTotal"
            :page-size="userPageSize"
            :current-page="userCurrentPage"
            @size-change="handleUserSizeChange"
            @current-change="handleUserPageChange"
            :page-sizes="[10, 20, 50]"
          />
        </div>
        <!-- 新增/编辑用户弹窗 -->
        <el-dialog :title="userDialogTitle" v-model="userDialogVisible" width="500px" @close="handleUserDialogClose">
          <el-form :model="userForm" :rules="userFormRules" ref="userFormRef" label-width="100px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="userForm.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userForm.email" autocomplete="off" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userForm.phone" autocomplete="off" />
            </el-form-item>
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="userForm.realName" autocomplete="off" />
            </el-form-item>
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="userForm.idCard" autocomplete="off" />
            </el-form-item>
            <el-form-item label="角色" prop="role">
              <el-select v-model="userForm.role" placeholder="请选择角色">
                <el-option label="普通用户" value="USER" />
                <el-option label="管理员" value="ADMIN" />
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="userForm.status" placeholder="请选择状态">
                <el-option label="正常" value="ACTIVE" />
                <el-option label="禁用" value="NATIVE" />
              </el-select>
            </el-form-item>
            <el-form-item v-if="!userForm.id" label="密码" prop="password">
              <el-input v-model="userForm.password" type="password" autocomplete="off" />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="userDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="submitUserForm" :loading="userSubmitLoading">确认</el-button>
            </span>
          </template>
        </el-dialog>
        <!-- 用户详情弹窗 -->
        <el-dialog title="用户详情" v-model="userViewDialogVisible" width="500px">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="ID">{{ userViewData.id }}</el-descriptions-item>
            <el-descriptions-item label="用户名">{{ userViewData.username }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ userViewData.email }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ userViewData.phone }}</el-descriptions-item>
            <el-descriptions-item label="真实姓名">{{ userViewData.realName }}</el-descriptions-item>
            <el-descriptions-item label="身份证号">{{ userViewData.idCard }}</el-descriptions-item>
            <el-descriptions-item label="角色">{{ userViewData.role === 'ADMIN' ? '管理员' : '普通用户' }}</el-descriptions-item>
            <el-descriptions-item label="状态">{{ userViewData.status === 'ACTIVE' ? '正常' : '禁用' }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ formatTime(userViewData.createTime) }}</el-descriptions-item>
            <el-descriptions-item label="更新时间">{{ formatTime(userViewData.updateTime) }}</el-descriptions-item>
          </el-descriptions>
        </el-dialog>
      </div>

      <!-- 公告管理内容 -->
      <div v-if="activeMenu === 'announcement'" class="announcement-content">
        <!-- 搜索栏 -->
        <el-form :inline="true" :model="announcementSearchForm" class="search-form">
          <el-form-item label="标题">
            <el-input v-model="announcementSearchForm.title" placeholder="请输入公告标题" clearable />
          </el-form-item>
          <el-form-item label="类型">
            <el-select v-model="announcementSearchForm.type" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="系统" value="SYSTEM" />
              <el-option label="演唱会" value="CONCERT" />
              <el-option label="其他" value="OTHER" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="announcementSearchForm.status" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="已发布" value="PUBLISHED" />
              <el-option label="草稿" value="DRAFT" />
            </el-select>
          </el-form-item>
          <el-form-item label="优先级">
            <el-select v-model="announcementSearchForm.priority" placeholder="全部" clearable style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="高" value="HIGH" />
              <el-option label="中" value="MEDIUM" />
              <el-option label="低" value="LOW" />
            </el-select>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="announcementSearchForm.author" placeholder="请输入作者" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onAnnouncementSearch">搜索</el-button>
            <el-button @click="onAnnouncementReset">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="showAnnouncementAddDialog" plain>+ 添加公告</el-button>
          </el-form-item>
        </el-form>
        <!-- 公告表格 -->
        <el-table :data="announcements" border stripe class="announcement-table" v-loading="announcementLoading">
          <el-table-column prop="id" label="ID" width="60" align="center" />
          <el-table-column prop="title" label="标题" min-width="160" />
          <el-table-column prop="type" label="类型" min-width="80">
            <template #default="scope">
              <el-tag v-if="scope.row.type === 'SYSTEM'" type="info">系统</el-tag>
              <el-tag v-else-if="scope.row.type === 'CONCERT'" type="success">演唱会</el-tag>
              <el-tag v-else type="default">其他</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="priority" label="优先级" min-width="80">
            <template #default="scope">
              <el-tag v-if="scope.row.priority === 'HIGH'" type="danger">高</el-tag>
              <el-tag v-else-if="scope.row.priority === 'MEDIUM'" type="warning">中</el-tag>
              <el-tag v-else type="info">低</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" min-width="80">
            <template #default="scope">
              <el-tag v-if="scope.row.status === 'PUBLISHED'" type="success">已发布</el-tag>
              <el-tag v-else type="info">草稿</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="publishTime" label="发布时间" min-width="160">
            <template #default="scope">{{ formatTime(scope.row.publishTime) }}</template>
          </el-table-column>
          <el-table-column prop="author" label="作者" min-width="100" />
          <el-table-column label="操作" min-width="160">
            <template #default="scope">
              <el-link type="primary" @click="onAnnouncementView(scope.row)"><i class="el-icon-view"></i> 查看</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="info" @click="onAnnouncementEdit(scope.row)"><i class="el-icon-edit"></i> 编辑</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link type="danger" @click="onAnnouncementDelete(scope.row)"><i class="el-icon-delete"></i> 删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="pagination-bar">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="announcementTotal"
            :page-size="announcementPageSize"
            :current-page="announcementCurrentPage"
            @size-change="handleAnnouncementSizeChange"
            @current-change="handleAnnouncementPageChange"
            :page-sizes="[10, 20, 50]"
          />
        </div>
        <!-- 新增/编辑公告弹窗 -->
        <el-dialog :title="announcementDialogTitle" v-model="announcementDialogVisible" width="600px" @close="handleAnnouncementDialogClose">
          <el-form :model="announcementForm" :rules="announcementFormRules" ref="announcementFormRef" label-width="100px">
            <el-form-item label="标题" prop="title">
              <el-input v-model="announcementForm.title" autocomplete="off" />
            </el-form-item>
            <el-form-item label="内容" prop="content">
              <el-input v-model="announcementForm.content" type="textarea" :rows="4" autocomplete="off" />
            </el-form-item>
            <el-form-item label="类型" prop="type">
              <el-select v-model="announcementForm.type" placeholder="请选择类型">
                <el-option label="系统" value="SYSTEM" />
                <el-option label="演唱会" value="CONCERT" />
                <el-option label="其他" value="OTHER" />
              </el-select>
            </el-form-item>
            <el-form-item label="优先级" prop="priority">
              <el-select v-model="announcementForm.priority" placeholder="请选择优先级">
                <el-option label="高" value="HIGH" />
                <el-option label="中" value="MEDIUM" />
                <el-option label="低" value="LOW" />
              </el-select>
            </el-form-item>
            <el-form-item label="目标受众" prop="targetAudience">
              <el-input v-model="announcementForm.targetAudience" autocomplete="off" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="announcementForm.status" placeholder="请选择状态">
                <el-option label="已发布" value="PUBLISHED" />
                <el-option label="草稿" value="DRAFT" />
              </el-select>
            </el-form-item>
            <el-form-item label="发布时间" prop="publishTime">
              <el-date-picker v-model="announcementForm.publishTime" type="datetime" placeholder="选择发布时间" style="width: 100%" />
            </el-form-item>
            <el-form-item label="失效时间" prop="expireTime">
              <el-date-picker v-model="announcementForm.expireTime" type="datetime" placeholder="选择失效时间" style="width: 100%" />
            </el-form-item>
            <el-form-item label="作者" prop="author">
              <el-input v-model="announcementForm.author" autocomplete="off" />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="announcementDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="submitAnnouncementForm" :loading="announcementSubmitLoading">确认</el-button>
            </span>
          </template>
        </el-dialog>
        <!-- 公告详情弹窗 -->
        <el-dialog title="公告详情" v-model="announcementViewDialogVisible" width="600px">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="ID">{{ announcementViewData.id }}</el-descriptions-item>
            <el-descriptions-item label="标题">{{ announcementViewData.title }}</el-descriptions-item>
            <el-descriptions-item label="内容">{{ announcementViewData.content }}</el-descriptions-item>
            <el-descriptions-item label="类型">{{ getAnnouncementTypeName(announcementViewData.type) }}</el-descriptions-item>
            <el-descriptions-item label="优先级">{{ getAnnouncementPriorityName(announcementViewData.priority) }}</el-descriptions-item>
            <el-descriptions-item label="目标受众">{{ announcementViewData.targetAudience }}</el-descriptions-item>
            <el-descriptions-item label="状态">{{ getAnnouncementStatusName(announcementViewData.status) }}</el-descriptions-item>
            <el-descriptions-item label="发布时间">{{ formatTime(announcementViewData.publishTime) }}</el-descriptions-item>
            <el-descriptions-item label="失效时间">{{ formatTime(announcementViewData.expireTime) }}</el-descriptions-item>
            <el-descriptions-item label="作者">{{ announcementViewData.author }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ formatTime(announcementViewData.createTime) }}</el-descriptions-item>
            <el-descriptions-item label="更新时间">{{ formatTime(announcementViewData.updateTime) }}</el-descriptions-item>
          </el-descriptions>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

const router = useRouter()
const activeMenu = ref('dashboard')

// 演唱会管理相关数据
const searchForm = ref({ title: '', artist: '', status: '' })
const concerts = ref([])
const total = ref(0)
const pageSize = ref(10)
const currentPage = ref(1)
const loading = ref(false)

// 对话框相关数据
const dialogVisible = ref(false)
const dialogTitle = ref('添加演唱会')
const submitLoading = ref(false)
const concertFormRef = ref()
const concertForm = ref<{
  id: number | null;
  title: string;
  artist: string;
  venue: string;
  venueAddress: string;
  concertTime: string;
  duration: number;
  price: number;
  totalSeats: number;
  category: string;
  status: string;
  posterUrl: string;
  description: string;
}>({
  id: null,
  title: '',
  artist: '',
  venue: '',
  venueAddress: '',
  concertTime: '',
  duration: 120,
  price: 0,
  totalSeats: 1000,
  category: 'POP',
  status: 'UPCOMING',
  posterUrl: '',
  description: ''
})

// 表单验证规则
const concertRules = {
  title: [{ required: true, message: '请输入演唱会名称', trigger: 'blur' }],
  artist: [{ required: true, message: '请输入艺人名称', trigger: 'blur' }],
  venue: [{ required: true, message: '请输入场馆名称', trigger: 'blur' }],
  concertTime: [{ required: true, message: '请选择演出时间', trigger: 'change' }],
  price: [{ required: true, message: '请输入票价', trigger: 'blur' }],
  totalSeats: [{ required: true, message: '请输入总座位数', trigger: 'blur' }]
}

// 查看详情相关
const viewDialogVisible = ref(false)
const viewConcert = ref<any>(null)

// 订单管理相关数据
const orderSearchForm = ref({ orderNo: '', userId: '', paymentStatus: '', orderStatus: '' })
const orders = ref([])
const orderTotal = ref(0)
const orderPageSize = ref(10)
const orderCurrentPage = ref(1)
const orderLoading = ref(false)

// 订单查看详情相关
const orderViewDialogVisible = ref(false)
const viewOrder = ref<any>(null)

// 订单支付相关
const orderPayDialogVisible = ref(false)
const orderPayLoading = ref(false)
const orderPayForm = ref({
  id: null,
  orderNo: '',
  totalAmount: 0,
  paymentMethod: 'ALIPAY'
})

// 用户管理相关数据
const userSearchForm = ref({ username: '', phone: '', role: '', status: '' })
const users = ref([])
const userTotal = ref(0)
const userPageSize = ref(10)
const userCurrentPage = ref(1)
const userLoading = ref(false)

const userDialogVisible = ref(false)
const userDialogTitle = ref('添加用户')
const userSubmitLoading = ref(false)
const userFormRef = ref()
const userForm = ref<any>({
  id: null,
  username: '',
  password: '',
  email: '',
  phone: '',
  realName: '',
  idCard: '',
  role: 'USER',
  status: 'ACTIVE'
})
const userFormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}
const userViewDialogVisible = ref(false)
const userViewData = ref<any>({})

// 公告管理相关数据
const announcementSearchForm = ref({ title: '', type: '', status: '', priority: '', author: '' })
const announcements = ref([])
const announcementTotal = ref(0)
const announcementPageSize = ref(10)
const announcementCurrentPage = ref(1)
const announcementLoading = ref(false)

const announcementDialogVisible = ref(false)
const announcementDialogTitle = ref('添加公告')
const announcementSubmitLoading = ref(false)
const announcementFormRef = ref()
const announcementForm = ref<any>({
  id: null,
  title: '',
  content: '',
  type: 'SYSTEM',
  priority: 'MEDIUM',
  targetAudience: '',
  status: 'DRAFT',
  publishTime: '',
  expireTime: '',
  author: ''
})
const announcementFormRules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  priority: [{ required: true, message: '请选择优先级', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }]
}
const announcementViewDialogVisible = ref(false)
const announcementViewData = ref<any>({})

const handleMenuSelect = (key: string) => {
  activeMenu.value = key
  if (key === 'concert') {
    loadConcerts()
  } else if (key === 'order') {
    loadOrders()
  } else if (key === 'user') {
    loadUsers()
  } else if (key === 'announcement') {
    loadAnnouncements()
  }
}

const getHeaderTitle = () => {
  const titles: Record<string, string> = {
    dashboard: '管理后台 / 数据统计',
    analysis: '管理后台 / 数据分析',
    concert: '管理后台 / 演唱会管理',
    order: '管理后台 / 订单管理',
    user: '管理后台 / 用户管理',
    announcement: '管理后台 / 公告管理'
  }
  return titles[activeMenu.value] || '管理后台'
}

const goHome = () => router.push('/')

// 演唱会管理相关方法
const loadConcerts = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/concert/page', {
      params: {
        currentPage: currentPage.value,
        size: pageSize.value
      }
    })
    if (response.data.success) {
      concerts.value = response.data.concerts
      total.value = response.data.total
    } else {
      ElMessage.error(response.data.message || '获取演唱会列表失败')
    }
  } catch (error) {
    console.error('获取演唱会列表失败:', error)
    ElMessage.error('获取演唱会列表失败')
  } finally {
    loading.value = false
  }
}

const onSearch = async () => {
  loading.value = true
  try {
    const params: Record<string, any> = {
      currentPage: currentPage.value,
      size: pageSize.value
    }
    if (searchForm.value.title) params.title = searchForm.value.title
    if (searchForm.value.artist) params.artist = searchForm.value.artist
    if (searchForm.value.status) params.status = searchForm.value.status
    
    const response = await axios.get('/api/concert/page', { params })
    if (response.data.success) {
      concerts.value = response.data.concerts
      total.value = response.data.total
    } else {
      ElMessage.error(response.data.message || '搜索失败')
    }
  } catch (error) {
    console.error('搜索失败:', error)
    ElMessage.error('搜索失败')
  } finally {
    loading.value = false
  }
}

const onReset = () => { 
  searchForm.value = { title: '', artist: '', status: '' }
  currentPage.value = 1
  loadConcerts()
}

const showAddDialog = () => {
  dialogTitle.value = '添加演唱会'
  concertForm.value = {
    id: null,
    title: '',
    artist: '',
    venue: '',
    venueAddress: '',
    concertTime: '',
    duration: 120,
    price: 0,
    totalSeats: 1000,
    category: 'POP',
    status: 'UPCOMING',
    posterUrl: '',
    description: ''
  } as any
  dialogVisible.value = true
}

const onEdit = (row: any) => {
  dialogTitle.value = '编辑演唱会'
  concertForm.value = { ...row }
  dialogVisible.value = true
}

const onView = async (row: any) => {
  try {
    const response = await axios.get(`/api/concert/${row.id}`)
    if (response.data.success) {
      viewConcert.value = response.data.concert
      viewDialogVisible.value = true
    } else {
      ElMessage.error(response.data.message || '获取演唱会详情失败')
    }
  } catch (error) {
    console.error('获取演唱会详情失败:', error)
    ElMessage.error('获取演唱会详情失败')
  }
}

const onDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这个演唱会吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/concert/${row.id}`)
    if (response.data.success) {
      ElMessage.success('删除成功')
      loadConcerts()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const submitForm = async () => {
  if (!concertFormRef.value) return
  
  try {
    await concertFormRef.value.validate()
    submitLoading.value = true
    
    let response
    if (concertForm.value.id) {
      // 编辑
      response = await axios.put(`/api/concert/${concertForm.value.id}`, concertForm.value)
    } else {
      // 添加
      response = await axios.post('/api/concert', concertForm.value)
    }
    
    if (response.data.success) {
      ElMessage.success(concertForm.value.id ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadConcerts()
    } else {
      ElMessage.error(response.data.message || (concertForm.value.id ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error)
      ElMessage.error('提交失败')
    }
  } finally {
    submitLoading.value = false
  }
}

const handleDialogClose = () => {
  dialogVisible.value = false
  if (concertFormRef.value) {
    concertFormRef.value.resetFields()
  }
}

const handleSizeChange = (size: number) => { 
  pageSize.value = size 
  currentPage.value = 1
  if (searchForm.value.title || searchForm.value.artist || searchForm.value.status) {
    onSearch()
  } else {
    loadConcerts()
  }
}

const handlePageChange = (page: number) => { 
  currentPage.value = page 
  if (searchForm.value.title || searchForm.value.artist || searchForm.value.status) {
    onSearch()
  } else {
    loadConcerts()
  }
}

const formatTime = (time: string) => time ? time.replace('T', ' ').slice(0, 16) : ''

const formatPriceRange = (price: number) => {
  if (!price) return '暂无价格'
  const vipPrice = Math.round(price * 1.2)
  const economyPrice = Math.round(price * 0.8)
  return `${economyPrice}-${vipPrice}`
}

const getCategoryName = (category: string) => {
  const categoryMap: Record<string, string> = {
    'POP': '流行',
    'ROCK': '摇滚',
    'CLASSICAL': '古典',
    'JAZZ': '爵士',
    'FOLK': '民谣',
    'ELECTRONIC': '电子',
    'OTHER': '其他'
  }
  return categoryMap[category] || category
}

// 订单管理相关方法
const loadOrders = async () => {
  orderLoading.value = true
  try {
    const response = await axios.get('/api/order/list')
    if (response.data.success) {
      orders.value = response.data.orders
      orderTotal.value = orders.value.length
    } else {
      ElMessage.error(response.data.message || '获取订单列表失败')
    }
  } catch (error) {
    console.error('获取订单列表失败:', error)
    ElMessage.error('获取订单列表失败')
  } finally {
    orderLoading.value = false
  }
}

const onOrderSearch = async () => {
  orderLoading.value = true
  try {
    const params: Record<string, string> = {}
    if (orderSearchForm.value.orderNo) params.orderNo = orderSearchForm.value.orderNo
    if (orderSearchForm.value.userId) params.userId = orderSearchForm.value.userId
    if (orderSearchForm.value.paymentStatus) params.paymentStatus = orderSearchForm.value.paymentStatus
    if (orderSearchForm.value.orderStatus) params.orderStatus = orderSearchForm.value.orderStatus
    
    const response = await axios.get('/api/order/list', { params })
    if (response.data.success) {
      orders.value = response.data.orders
      orderTotal.value = orders.value.length
    } else {
      ElMessage.error(response.data.message || '搜索失败')
    }
  } catch (error) {
    console.error('搜索失败:', error)
    ElMessage.error('搜索失败')
  } finally {
    orderLoading.value = false
  }
}

const onOrderReset = () => { 
  orderSearchForm.value = { orderNo: '', userId: '', paymentStatus: '', orderStatus: '' }
  loadOrders()
}

const onOrderView = async (row: any) => {
  try {
    const response = await axios.get(`/api/order/${row.id}`)
    if (response.data.success) {
      viewOrder.value = response.data.order
      orderViewDialogVisible.value = true
    } else {
      ElMessage.error(response.data.message || '获取订单详情失败')
    }
  } catch (error) {
    console.error('获取订单详情失败:', error)
    ElMessage.error('获取订单详情失败')
  }
}

const onOrderPay = (row: any) => {
  orderPayForm.value = {
    id: row.id,
    orderNo: row.orderNo,
    totalAmount: row.totalAmount,
    paymentMethod: 'ALIPAY'
  }
  orderPayDialogVisible.value = true
}

const submitOrderPay = async () => {
  try {
    orderPayLoading.value = true
    const response = await axios.post(`/api/order/${orderPayForm.value.id}/pay`, {
      paymentMethod: orderPayForm.value.paymentMethod
    })
    if (response.data.success) {
      ElMessage.success('支付成功')
      orderPayDialogVisible.value = false
      loadOrders()
    } else {
      ElMessage.error(response.data.message || '支付失败')
    }
  } catch (error) {
    console.error('支付失败:', error)
    ElMessage.error('支付失败')
  } finally {
    orderPayLoading.value = false
  }
}

const onOrderCancel = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要取消这个订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.post(`/api/order/${row.id}/cancel`)
    if (response.data.success) {
      ElMessage.success('订单取消成功')
      loadOrders()
    } else {
      ElMessage.error(response.data.message || '订单取消失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('订单取消失败:', error)
      ElMessage.error('订单取消失败')
    }
  }
}

const onOrderDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这个订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/order/${row.id}`)
    if (response.data.success) {
      ElMessage.success('删除成功')
      loadOrders()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const handleOrderSizeChange = (size: number) => { 
  orderPageSize.value = size 
  loadOrders()
}

const handleOrderPageChange = (page: number) => { 
  orderCurrentPage.value = page 
  loadOrders()
}

const getPaymentMethodName = (method: string) => {
  const methodMap: Record<string, string> = {
    'ALIPAY': '支付宝',
    'WECHAT': '微信支付',
    'BANK': '银行卡'
  }
  return methodMap[method] || method
}

// 用户管理相关方法
const loadUsers = async () => {
  userLoading.value = true
  try {
    const params: Record<string, any> = {
      currentPage: userCurrentPage.value,
      size: userPageSize.value
    }
    if (userSearchForm.value.username) params.username = userSearchForm.value.username
    if (userSearchForm.value.phone) params.phone = userSearchForm.value.phone
    if (userSearchForm.value.role) params.role = userSearchForm.value.role
    if (userSearchForm.value.status) params.status = userSearchForm.value.status
    const response = await axios.get('/api/user/page', { params })
    if (response.data.success) {
      users.value = response.data.users || response.data.data || response.data.list || []
      userTotal.value = response.data.total || 0
    } else {
      ElMessage.error(response.data.message || '获取用户列表失败')
    }
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    userLoading.value = false
  }
}
const onUserSearch = () => {
  userCurrentPage.value = 1
  loadUsers()
}
const onUserReset = () => {
  userSearchForm.value = { username: '', phone: '', role: '', status: '' }
  userCurrentPage.value = 1
  loadUsers()
}
const handleUserSizeChange = (size: number) => {
  userPageSize.value = size
  userCurrentPage.value = 1
  loadUsers()
}
const handleUserPageChange = (page: number) => {
  userCurrentPage.value = page
  loadUsers()
}
const showUserAddDialog = () => {
  userDialogTitle.value = '添加用户'
  userForm.value = {
    id: null,
    username: '',
    password: '',
    email: '',
    phone: '',
    realName: '',
    idCard: '',
    role: 'USER',
    status: 'ACTIVE'
  }
  userDialogVisible.value = true
}
const onUserEdit = (row: any) => {
  userDialogTitle.value = '编辑用户'
  userForm.value = { ...row, password: '' }
  userDialogVisible.value = true
}
const onUserView = async (row: any) => {
  try {
    const response = await axios.get(`/api/user/${row.id}`)
    if (response.data.success) {
      userViewData.value = response.data.user || response.data.data
      userViewDialogVisible.value = true
    } else {
      ElMessage.error(response.data.message || '获取用户详情失败')
    }
  } catch (error) {
    console.error('获取用户详情失败:', error)
    ElMessage.error('获取用户详情失败')
  }
}
const onUserDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这个用户吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    const response = await axios.delete(`/api/user/${row.id}`)
    if (response.data.success) {
      ElMessage.success('删除成功')
      loadUsers()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}
const submitUserForm = async () => {
  if (!userFormRef.value) return
  try {
    await userFormRef.value.validate()
    userSubmitLoading.value = true
    let response
    if (userForm.value.id) {
      // 编辑
      const data = { ...userForm.value }
      if (!data.password) delete data.password // 不修改密码时不传
      response = await axios.put(`/api/user/${userForm.value.id}`, data)
    } else {
      // 新增
      response = await axios.post('/api/user', userForm.value)
    }
    if (response.data.success) {
      ElMessage.success(userForm.value.id ? '更新成功' : '添加成功')
      userDialogVisible.value = false
      loadUsers()
    } else {
      ElMessage.error(response.data.message || (userForm.value.id ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error)
      ElMessage.error('提交失败')
    }
  } finally {
    userSubmitLoading.value = false
  }
}
const handleUserDialogClose = () => {
  userDialogVisible.value = false
  if (userFormRef.value) {
    userFormRef.value.resetFields()
  }
}

// 公告管理方法
const loadAnnouncements = async () => {
  announcementLoading.value = true
  try {
    const params: Record<string, any> = {
      currentPage: announcementCurrentPage.value,
      size: announcementPageSize.value
    }
    if (announcementSearchForm.value.title) params.title = announcementSearchForm.value.title
    if (announcementSearchForm.value.type) params.type = announcementSearchForm.value.type
    if (announcementSearchForm.value.status) params.status = announcementSearchForm.value.status
    if (announcementSearchForm.value.priority) params.priority = announcementSearchForm.value.priority
    if (announcementSearchForm.value.author) params.author = announcementSearchForm.value.author
    const response = await axios.get('/api/announcement/page', { params })
    if (response.data.success) {
      announcements.value = response.data.announcements || response.data.data || response.data.list || []
      announcementTotal.value = response.data.total || 0
    } else {
      ElMessage.error(response.data.message || '获取公告列表失败')
    }
  } catch (error) {
    console.error('获取公告列表失败:', error)
    ElMessage.error('获取公告列表失败')
  } finally {
    announcementLoading.value = false
  }
}
const onAnnouncementSearch = () => {
  announcementCurrentPage.value = 1
  loadAnnouncements()
}
const onAnnouncementReset = () => {
  announcementSearchForm.value = { title: '', type: '', status: '', priority: '', author: '' }
  announcementCurrentPage.value = 1
  loadAnnouncements()
}
const handleAnnouncementSizeChange = (size: number) => {
  announcementPageSize.value = size
  announcementCurrentPage.value = 1
  loadAnnouncements()
}
const handleAnnouncementPageChange = (page: number) => {
  announcementCurrentPage.value = page
  loadAnnouncements()
}
const showAnnouncementAddDialog = () => {
  announcementDialogTitle.value = '添加公告'
  announcementForm.value = {
    id: null,
    title: '',
    content: '',
    type: 'SYSTEM',
    priority: 'MEDIUM',
    targetAudience: '',
    status: 'DRAFT',
    publishTime: '',
    expireTime: '',
    author: ''
  }
  announcementDialogVisible.value = true
}
const onAnnouncementEdit = (row: any) => {
  announcementDialogTitle.value = '编辑公告'
  announcementForm.value = { ...row }
  announcementDialogVisible.value = true
}
const onAnnouncementView = async (row: any) => {
  try {
    const response = await axios.get(`/api/announcement/${row.id}`)
    if (response.data.success) {
      announcementViewData.value = response.data.announcement || response.data.data
      announcementViewDialogVisible.value = true
    } else {
      ElMessage.error(response.data.message || '获取公告详情失败')
    }
  } catch (error) {
    console.error('获取公告详情失败:', error)
    ElMessage.error('获取公告详情失败')
  }
}
const onAnnouncementDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这个公告吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    const response = await axios.delete(`/api/announcement/${row.id}`)
    if (response.data.success) {
      ElMessage.success('删除成功')
      loadAnnouncements()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}
const submitAnnouncementForm = async () => {
  if (!announcementFormRef.value) return
  try {
    await announcementFormRef.value.validate()
    announcementSubmitLoading.value = true
    let response
    if (announcementForm.value.id) {
      // 编辑
      response = await axios.put(`/api/announcement/${announcementForm.value.id}`, announcementForm.value)
    } else {
      // 新增
      response = await axios.post('/api/announcement', announcementForm.value)
    }
    if (response.data.success) {
      ElMessage.success(announcementForm.value.id ? '更新成功' : '添加成功')
      announcementDialogVisible.value = false
      loadAnnouncements()
    } else {
      ElMessage.error(response.data.message || (announcementForm.value.id ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error)
      ElMessage.error('提交失败')
    }
  } finally {
    announcementSubmitLoading.value = false
  }
}
const handleAnnouncementDialogClose = () => {
  announcementDialogVisible.value = false
  if (announcementFormRef.value) {
    announcementFormRef.value.resetFields()
  }
}
const getAnnouncementTypeName = (type: string) => {
  if (type === 'SYSTEM') return '系统'
  if (type === 'CONCERT') return '演唱会'
  return '其他'
}
const getAnnouncementPriorityName = (priority: string) => {
  if (priority === 'HIGH') return '高'
  if (priority === 'MEDIUM') return '中'
  return '低'
}
const getAnnouncementStatusName = (status: string) => {
  if (status === 'PUBLISHED') return '已发布'
  return '草稿'
}

// 页面加载时获取数据
onMounted(() => {
  if (activeMenu.value === 'concert') {
    loadConcerts()
  } else if (activeMenu.value === 'order') {
    loadOrders()
  } else if (activeMenu.value === 'user') {
    loadUsers()
  } else if (activeMenu.value === 'announcement') {
    loadAnnouncements()
  }
})
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background: #f5f6fa;
}
.sidebar {
  width: 220px;
  background: #1a2233;
  color: #fff;
  min-height: 100vh;
  box-shadow: 2px 0 8px rgba(0,0,0,0.04);
  display: flex;
  flex-direction: column;
}
.sidebar-title {
  font-size: 22px;
  font-weight: bold;
  padding: 32px 0 24px 32px;
  letter-spacing: 2px;
}
.sidebar-menu {
  background: transparent;
  border-right: none;
}
.sidebar-menu .el-menu-item {
  color: #fff;
  font-size: 16px;
  height: 48px;
  line-height: 48px;
}
.sidebar-menu .el-menu-item.is-active {
  background: #273352;
  color: #ffd04b;
}
.main-content {
  flex: 1;
  padding: 0 32px 32px 32px;
}
.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  background: #fff;
  border-radius: 0 0 10px 10px;
  margin-bottom: 24px;
  padding: 0 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}
.header-title {
  font-size: 18px;
  font-weight: bold;
  color: #222;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}
.admin-username {
  margin-left: 8px;
  font-size: 15px;
  color: #333;
}
.admin-role {
  color: #999;
  font-size: 13px;
  margin-left: 4px;
}

/* 数据概览样式 */
.dashboard-content .overview-section {
  margin-bottom: 24px;
}
.overview-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 0 16px 0;
  border-radius: 10px;
  min-height: 120px;
}
.overview-icon {
  font-size: 32px;
  margin-bottom: 12px;
  border-radius: 50%;
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.overview-icon.purple { background: #e6e6fa; color: #7c3aed; }
.overview-icon.pink { background: #ffe4ec; color: #f43f5e; }
.overview-icon.blue { background: #e0f2fe; color: #2563eb; }
.overview-icon.green { background: #e0fbe6; color: #22c55e; }
.overview-title {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 4px;
}
.overview-label {
  color: #888;
  font-size: 15px;
}
.dashboard-content .today-section {
  margin-bottom: 24px;
}
.today-card {
  text-align: center;
  padding: 18px 0;
  border-radius: 10px;
}
.today-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 4px;
}
.today-label {
  color: #888;
  font-size: 15px;
}
.dashboard-content .quick-section {
  margin-bottom: 24px;
}
.quick-card {
  min-height: 180px;
  border-radius: 10px;
  margin-bottom: 0;
}
.quick-title {
  font-size: 17px;
  font-weight: bold;
  margin-bottom: 12px;
}
.quick-desc {
  color: #888;
  font-size: 14px;
  margin-bottom: 8px;
}
.activity-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.activity-list li {
  font-size: 15px;
  color: #444;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
}
.activity-list .activity-time {
  color: #aaa;
  font-size: 13px;
  margin-left: 8px;
}

/* 演唱会管理样式 */
.concert-content {
  padding: 0;
}
.search-form {
  background: #fff;
  padding: 18px 24px 0 24px;
  border-radius: 10px;
  margin-bottom: 18px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}
.concert-table {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  margin-bottom: 18px;
}
.pagination-bar {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  padding: 16px 0 8px 0;
  display: flex;
  justify-content: flex-end;
}

/* 其他内容样式 */
.other-content {
  padding: 0;
}
.content-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
  color: #222;
}

/* 演唱会详情样式 */
.concert-detail {
  padding: 16px 0;
}
.poster-preview {
  margin-top: 20px;
  text-align: center;
}
.poster-preview h4 {
  margin-bottom: 12px;
  color: #333;
}

/* 订单管理样式 */
.order-content {
  padding: 0;
}
.order-table {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  margin-bottom: 18px;
}

/* 订单详情样式 */
.order-detail {
  padding: 16px 0;
}
.qr-preview {
  margin-top: 20px;
  text-align: center;
}
.qr-preview h4 {
  margin-bottom: 12px;
  color: #333;
}
.qr-code {
  background: #f5f5f5;
  padding: 16px;
  border-radius: 8px;
  font-family: monospace;
  font-size: 14px;
  word-break: break-all;
}

/* 用户管理样式 */
.user-content {
  padding: 0;
}
.user-table {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  margin-bottom: 18px;
}

/* 公告管理样式 */
.announcement-content {
  padding: 0;
}
.announcement-table {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  margin-bottom: 18px;
}
</style>

@media (max-width: 900px) {
  .main-content { padding: 0 4px 24px 4px; }
  .admin-header { padding: 0 8px; }
} 