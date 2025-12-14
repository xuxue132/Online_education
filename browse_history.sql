-- 浏览历史记录表
CREATE TABLE IF NOT EXISTS `browse_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_phone` varchar(20) NOT NULL COMMENT '用户手机号',
  `news_id` int(11) NOT NULL COMMENT '新闻ID',
  `browse_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  `deletes` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_phone` (`user_phone`),
  KEY `idx_news_id` (`news_id`),
  KEY `idx_browse_time` (`browse_time`),
  UNIQUE KEY `uk_user_news` (`user_phone`, `news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户浏览历史记录表';