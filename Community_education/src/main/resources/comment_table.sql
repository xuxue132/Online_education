-- 创建评论表
CREATE TABLE IF NOT EXISTS `comment` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID',
    `news_id` INT NOT NULL COMMENT '新闻ID',
    `user_id` INT NOT NULL COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `parent_id` INT DEFAULT 0 COMMENT '父评论ID（0表示一级评论）',
    `reply_user_id` INT DEFAULT NULL COMMENT '被回复的用户ID',
    `reply_username` VARCHAR(50) DEFAULT NULL COMMENT '被回复的用户名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
    INDEX `idx_news_id` (`news_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';