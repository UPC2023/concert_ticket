/*
feedback 表字段说明：

id           bigint         反馈ID，主键，自增。每条反馈的唯一标识。
user_id      bigint         用户ID，外键。指向 user 表的 id，表示是谁提交的反馈。
content      varchar(1000)  反馈内容。用户填写的意见或建议。
contact      varchar(255)   联系方式。用户可选填写（如邮箱、手机号），便于管理员联系。
status       varchar(32)    状态。PENDING-待处理，RESOLVED-已解决，REPLIED-已回复。
reply        varchar(1000)  回复内容。管理员对该反馈的回复。
create_time  datetime       创建时间。该反馈提交的时间。
update_time  datetime       更新时间。该反馈最后一次被修改的时间（如回复、状态变更等）。

主键：id
索引：user_id、status、create_time
外键：user_id 关联 user 表的 id 字段
表用途：用于存储用户反馈信息
*/

-- 原有SQL内容
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '反馈内容',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING' COMMENT '状态：PENDING-待处理，RESOLVED-已解决，REPLIED-已回复',
  `reply` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '回复内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  CONSTRAINT `fk_feedback_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户反馈表' ROW_FORMAT = Dynamic;