package org.demo02.controller;

import org.demo02.entity.Feedback;
import org.demo02.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    // 用户提交反馈
    @PostMapping
    public Map<String, Object> submitFeedback(@RequestBody Feedback feedback) {
        feedbackService.submitFeedback(feedback);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        return res;
    }

    // 管理员分页/条件查询反馈
    @GetMapping("/list")
    public List<Feedback> getFeedbackList(@RequestParam(required = false) Long userId,
                                          @RequestParam(required = false) String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("status", status);
        return feedbackService.getFeedbackList(params);
    }

    // 反馈详情
    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    // 管理员回复
    @PutMapping("/{id}/reply")
    public Map<String, Object> replyFeedback(@PathVariable Long id, @RequestBody Map<String, String> body) {
        feedbackService.replyFeedback(id, body.get("reply"));
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        return res;
    }

    // 管理员更改状态
    @PutMapping("/{id}/status")
    public Map<String, Object> updateFeedbackStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        feedbackService.updateFeedbackStatus(id, body.get("status"));
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        return res;
    }
}