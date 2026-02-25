CREATE TABLE `news_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `news_id` int(11) NOT NULL COMMENT '新闻ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_news` (`user_id`,`news_id`) COMMENT '用户和新闻联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻收藏表';
