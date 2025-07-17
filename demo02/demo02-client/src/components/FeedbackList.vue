<template>
  <div class="feedback-list">
    <h3>我的反馈</h3>
    <el-table :data="feedbacks" v-loading="loading" style="width: 100%">
      <el-table-column prop="content" label="反馈内容" min-width="200">
        <template #default="{ row }">
          <div class="content-cell">
            {{ row.content }}
          </div>
        </template>
      </el-table-column>
      
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">
            {{ getStatusText(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      
      <el-table-column prop="reply" label="管理员回复" min-width="200">
        <template #default="{ row }">
          <div v-if="row.reply" class="reply-content">
            {{ row.reply }}
          </div>
          <span v-else class="no-reply">暂无回复</span>
        </template>
      </el-table-column>
      
      <el-table-column prop="createTime" label="提交时间" width="180">
        <template #default="{ row }">
          {{ formatTime(row.createTime) }}
        </template>
      </el-table-column>
      
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button 
            type="primary" 
            size="small" 
            @click="viewDetail(row)"
          >
            查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 详情对话框 -->
    <el-dialog v-model="dialogVisible" title="反馈详情" width="600px">
      <div v-if="selectedFeedback" class="feedback-detail">
        <div class="detail-item">
          <label>反馈内容：</label>
          <div class="content">{{ selectedFeedback.content }}</div>
        </div>
        <div class="detail-item">
          <label>联系方式：</label>
          <div>{{ selectedFeedback.contact || '未提供' }}</div>
        </div>
        <div class="detail-item">
          <label>状态：</label>
          <el-tag :type="getStatusType(selectedFeedback.status)">
            {{ getStatusText(selectedFeedback.status) }}
          </el-tag>
        </div>
        <div class="detail-item">
          <label>提交时间：</label>
          <div>{{ formatTime(selectedFeedback.createTime) }}</div>
        </div>
        <div class="detail-item" v-if="selectedFeedback.reply">
          <label>管理员回复：</label>
          <div class="reply">{{ selectedFeedback.reply }}</div>
        </div>
        <div class="detail-item" v-if="selectedFeedback.reply">
          <label>回复时间：</label>
          <div>{{ formatTime(selectedFeedback.updateTime) }}</div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { getFeedbackList } from '../api/feedback';
import type { Feedback } from '../api/feedback';

// 数据
const feedbacks = ref<Feedback[]>([]);
const loading = ref(false);
const dialogVisible = ref(false);
const selectedFeedback = ref<Feedback | null>(null);

// 获取反馈列表
const fetchFeedbacks = async () => {
  try {
    loading.value = true;
    // 假设已登录用户ID为1，实际应从登录状态获取
    const res = await getFeedbackList({ userId: 1 });
    feedbacks.value = res.data || [];
  } catch (error) {
    ElMessage.error('获取反馈列表失败');
    console.error('获取反馈列表失败:', error);
  } finally {
    loading.value = false;
  }
};

// 查看详情
const viewDetail = (feedback: Feedback) => {
  selectedFeedback.value = feedback;
  dialogVisible.value = true;
};

// 获取状态类型
const getStatusType = (status?: string) => {
  switch (status) {
    case 'PENDING': return 'warning';
    case 'RESOLVED': return 'success';
    case 'REPLIED': return 'info';
    default: return 'info';
  }
};

// 获取状态文本
const getStatusText = (status?: string) => {
  switch (status) {
    case 'PENDING': return '待处理';
    case 'RESOLVED': return '已处理';
    case 'REPLIED': return '已回复';
    default: return '未知';
  }
};

// 格式化时间
const formatTime = (time?: string) => {
  if (!time) return '';
  return new Date(time).toLocaleString('zh-CN');
};

// 组件挂载时获取数据
onMounted(() => {
  fetchFeedbacks();
});
</script>

<style scoped>
.feedback-list {
  padding: 20px;
}

.feedback-list h3 {
  margin-bottom: 20px;
  color: #303133;
}

.content-cell {
  max-width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.reply-content {
  color: #409eff;
}

.no-reply {
  color: #909399;
  font-style: italic;
}

.feedback-detail {
  padding: 20px 0;
}

.detail-item {
  margin-bottom: 15px;
  display: flex;
  align-items: flex-start;
}

.detail-item label {
  font-weight: bold;
  min-width: 100px;
  color: #606266;
}

.detail-item .content,
.detail-item .reply {
  flex: 1;
  background-color: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
  white-space: pre-wrap;
  word-break: break-word;
}

.detail-item .reply {
  color: #409eff;
  background-color: #ecf5ff;
}
</style>


