<template>
    <div class="feedback-form">
      <h3>提交反馈</h3>
      <el-form @submit.prevent="onSubmit" :model="formData" :rules="rules" ref="formRef">
        <el-form-item label="反馈内容" prop="content">
          <el-input 
            v-model="formData.content" 
            type="textarea" 
            :rows="4"
            placeholder="请详细描述您的问题或建议..."
            required 
          />
        </el-form-item>
        
        <el-form-item label="联系方式" prop="contact">
          <el-input 
            v-model="formData.contact" 
            placeholder="邮箱或手机号（可选）"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="onSubmit" :loading="loading">
            提交反馈
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { ElMessage } from 'element-plus';
  // 修改导入语句
  import { submitFeedback } from '../api/feedback';
  import type { Feedback } from '../api/feedback';
  
  // 表单数据
  const formData = reactive<Feedback>({
    content: '',
    contact: '',
    userId: undefined
  });
  
  // 表单验证规则
  const rules = {
    content: [
      { required: true, message: '请输入反馈内容', trigger: 'blur' },
      { min: 10, message: '反馈内容至少10个字符', trigger: 'blur' }
    ]
  };
  
  // 表单引用
  const formRef = ref();
  const loading = ref(false);
  
  // 提交反馈
  const onSubmit = async () => {
    if (!formData.content.trim()) {
      ElMessage.warning('请输入反馈内容');
      return;
    }

    // 自动带上 userId
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      ElMessage.error('请先登录');
      return;
    }
    const user = JSON.parse(userStr);
    if (!user || !user.id) {
      ElMessage.error('用户信息无效，请重新登录');
      return;
    }
    formData.userId = user.id;

    try {
      loading.value = true;
      await submitFeedback(formData);
      ElMessage.success('反馈提交成功！');
      resetForm();
    } catch (error) {
      ElMessage.error('提交失败，请重试');
      console.error('提交反馈失败:', error);
    } finally {
      loading.value = false;
    }
  };
  
  // 重置表单
  const resetForm = () => {
    formData.content = '';
    formData.contact = '';
    // userId 不需要重置
    if (formRef.value) {
      formRef.value.resetFields();
    }
  };
  </script>
  
  <style scoped>
  .feedback-form {
    max-width: 600px;
    margin: 20px auto;
    padding: 20px;
    border: 1px solid #e4e7ed;
    border-radius: 8px;
    background-color: #fff;
  }
  
  .feedback-form h3 {
    margin-bottom: 20px;
    color: #303133;
    text-align: center;
  }
  
  .el-form-item {
    margin-bottom: 20px;
  }
  
  .el-button {
    margin-right: 10px;
  }
  </style>