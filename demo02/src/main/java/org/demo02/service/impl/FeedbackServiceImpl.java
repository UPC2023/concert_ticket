package org.demo02.service.impl;

import org.demo02.dao.FeedbackMapper;
import org.demo02.entity.Feedback;
import org.demo02.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public void submitFeedback(Feedback feedback) {
        feedbackMapper.insertFeedback(feedback);
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackMapper.selectFeedbackById(id);
    }

    @Override
    public List<Feedback> getFeedbackList(Map<String, Object> params) {
        return feedbackMapper.selectFeedbackList(params);
    }

    @Override
    public void replyFeedback(Long id, String reply) {
        feedbackMapper.updateFeedbackReply(id, reply);
    }

    @Override
    public void updateFeedbackStatus(Long id, String status) {
        feedbackMapper.updateFeedbackStatus(id, status);
    }
}