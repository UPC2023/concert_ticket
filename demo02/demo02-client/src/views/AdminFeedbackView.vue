<template>
  <div class="admin-feedback-view">
    <div class="header">
      <h2>反馈管理</h2>
    </div>
    <!-- 反馈管理列表 -->
    <div class="list-section">
      <FeedbackList />
    </div>
  </div>
</template>

<script lang="ts" setup>
import FeedbackList from '../components/FeedbackList.vue';
</script>

<style scoped>
.admin-feedback-view {
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

.list-section {
  background-color: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  overflow: hidden;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .admin-feedback-view {
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
