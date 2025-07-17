<template>
  <div class="user-feedback-view">
    <div class="header">
      <h2>用户反馈</h2>
      <el-button type="primary" @click="showForm = !showForm">
        {{ showForm ? '隐藏表单' : '提交反馈' }}
      </el-button>
    </div>
    <!-- 反馈提交表单 -->
    <div v-if="showForm" class="form-section">
      <FeedbackForm @submit-success="handleSubmitSuccess" />
    </div>
    <!-- 我的反馈列表，只查当前用户 -->
    <div class="list-section">
      <FeedbackList :user-id="currentUserId" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import FeedbackForm from '../components/FeedbackForm.vue';
import FeedbackList from '../components/FeedbackList.vue';

const showForm = ref(false);
const currentUserId = JSON.parse(localStorage.getItem('user') || '{}').id || null;

const handleSubmitSuccess = () => {
  showForm.value = false;
};
</script>

<style scoped>
.user-feedback-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
}

.header h2 {
  margin: 0;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
}

.form-section {
  margin-bottom: 40px;
  padding: 20px;
  background-color: #fafafa;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
}

.list-section {
  background-color: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  overflow: hidden;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-feedback-view {
    padding: 10px;
  }
  .header {
    flex-direction: column;
    gap: 15px;
    text-align: center;
  }
  .header h2 {
    font-size: 20px;
  }
}
</style>

