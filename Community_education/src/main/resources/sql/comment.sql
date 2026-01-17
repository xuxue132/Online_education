-- 创建评论表
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `news_id` int(11) NOT NULL COMMENT '新闻ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `content` text NOT NULL COMMENT '评论内容',
  `parent_id` int(11) DEFAULT 0 COMMENT '父评论ID，0表示一级评论',
  `reply_user_id` int(11) DEFAULT NULL COMMENT '被回复用户ID',
  `reply_username` varchar(50) DEFAULT NULL COMMENT '被回复用户名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除，0未删除，1已删除',
  PRIMARY KEY (`id`),
  INDEX `idx_news_id` (`news_id`),
  INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻评论表';