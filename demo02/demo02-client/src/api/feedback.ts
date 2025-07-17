import axios from 'axios';

export interface Feedback {
    id?: number;
    userId?: number;
    content: string;
    contact?: string;
    status?: string;
    reply?: string;
    createTime?: string;
    updateTime?: string;
}

export function submitFeedback(data: Feedback) {
    return axios.post('/api/feedback', data);
}

export function getFeedbackList(params?: { userId?: number; status?: string }) {
    return axios.get('/api/feedback/list', { params });
}

export function getFeedbackById(id: number) {
    return axios.get(`/api/feedback/${id}`);
}

export function replyFeedback(id: number, reply: string) {
    return axios.put(`/api/feedback/${id}/reply`, { reply });
}

export function updateFeedbackStatus(id: number, status: string) {
    return axios.put(`/api/feedback/${id}/status`, { status });
}