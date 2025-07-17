package org.demo02.dao;
import org.demo02.entity.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackMapper {
    int insertFeedback(Feedback feedback);
    Feedback selectFeedbackById(Long id);
    List<Feedback> selectFeedbackList(Map<String, Object> params);
    int updateFeedbackStatus(Long id, String status);
    int updateFeedbackReply(Long id, String reply);
}