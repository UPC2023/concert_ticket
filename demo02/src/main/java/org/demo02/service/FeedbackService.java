package org.demo02.service;
import org.demo02.entity.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackService {
    void submitFeedback(Feedback feedback);
    Feedback getFeedbackById(Long id);
    List<Feedback> getFeedbackList(Map<String, Object> params);
    void replyFeedback(Long id, String reply);
    void updateFeedbackStatus(Long id, String status);
}