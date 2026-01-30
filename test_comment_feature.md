# 新闻评论功能测试指南

## 启动项目

### 后端启动
```bash
cd /Users/xuxue/Documents/11823020317_徐学的副本/Money/Online_education/Community_education
mvn spring-boot:run
```

### 前端启动
```bash
cd /Users/xuxue/Documents/11823020317_徐学的副本/Money/Online_education/face_education
npm install
npm run serve
```

## 测试步骤

### 1. 创建新闻评论表
确保数据库中已创建news_comment表：
```sql
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
```

### 2. 登录系统
1. 打开浏览器，访问前端地址（通常是 http://localhost:8080）
2. 使用已有账号登录系统

### 3. 测试评论功能
1. 点击"新闻公告"进入新闻列表
2. 点击任意一条新闻查看详情
3. 在新闻详情页，应该能看到评论区域
4. 登录状态下，在评论输入框中输入评论内容，点击"发表评论"按钮
5. 验证评论是否成功显示在评论列表中
6. 点击某条评论的"回复"按钮，输入回复内容，点击"回复"按钮
7. 验证回复是否正确显示，并包含@用户名
8. 点击自己发表的评论的"删除"按钮，验证评论是否被成功删除

## 常见问题排查

### 1. "没有找到服务，请确认请求地址是否正确"
- 检查后端服务是否正常启动
- 检查浏览器控制台中的网络请求URL是否正确
- 确认后端Controller中的@RequestMapping路径与前端请求路径一致

### 2. "发表评论失败，请稍后重试"
- 检查数据库连接是否正常
- 确认news_comment表是否已创建
- 检查后端日志中的详细错误信息
- 确认用户是否已正确登录

### 3. 评论不显示
- 检查newsId是否正确传递给NewsComment组件
- 确认数据库中是否有对应的评论数据
- 检查前端组件中的数据渲染逻辑

## 技术实现要点

1. **前端组件**：NewsComment.vue 负责评论的展示、发表、回复和删除
2. **后端接口**：PublicController 提供评论相关的API接口
3. **数据模型**：NewsComment 实体类对应数据库表结构
4. **数据访问**：NewsCommentMapper 负责数据库操作
5. **业务逻辑**：NewsCommentImpl 实现评论相关的业务逻辑

## API接口

1. **获取评论列表**：POST /public/GetNewsComments
   - 参数：newsId
   - 返回：评论列表

2. **发表评论**：POST /public/AddComment
   - 参数：newsId, content, parentId（可选）
   - 返回：操作结果

3. **删除评论**：POST /public/DeleteComment
   - 参数：commentId
   - 返回：操作结果