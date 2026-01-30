-- 创建新闻评论表
CREATE TABLE `news_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `news_id` int(11) NOT NULL COMMENT '新闻ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（冗余存储，便于显示）',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父评论ID，0表示一级评论',
  `parent_username` varchar(50) DEFAULT NULL COMMENT '被回复的用户名，用于@功能',
  `content` text NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记，0表示未删除，1表示已删除',
  PRIMARY KEY (`id`),
  KEY `idx_news_id` (`news_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻评论表';