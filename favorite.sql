-- 新闻收藏表
CREATE TABLE news_favorite (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id INT NOT NULL COMMENT '用户ID，关联user_inf表',
    news_id INT NOT NULL COMMENT '新闻ID，关联New_Notice表',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    UNIQUE KEY uk_user_news (user_id, news_id) COMMENT '联合唯一索引，防止重复收藏'
) COMMENT '新闻收藏表';

-- 创建索引优化查询性能
CREATE INDEX idx_user_id ON news_favorite(user_id);
CREATE INDEX idx_news_id ON news_favorite(news_id);
CREATE INDEX idx_create_time ON news_favorite(create_time);
