# 在线教育网站 - 历史浏览记录功能

## 功能说明

我已经为你的在线教育网站成功添加了"历史浏览记录"功能，具体包括：

### 1. 后端功能
- **数据模型**：创建了`BrowseHistory`实体类和对应的Mapper接口
- **数据库表**：`browse_history`表，包含用户手机号、新闻ID、浏览时间等字段
- **业务逻辑**：
  - 用户浏览新闻时自动记录浏览历史
  - 同一篇新闻重复浏览会更新时间戳
  - 自动清理一周前的浏览记录
- **API接口**：
  - `/public/RecordBrowseHistory` - 记录浏览历史
  - `/public/UserBrowseHistory` - 获取用户浏览历史列表
  - `/public/UserBrowseHistoryCount` - 获取用户浏览历史总数

### 2. 前端功能
- **历史浏览记录页面**：`/BrowseHistory`路由
- **导航栏入口**：登录用户在导航栏可以看到"历史浏览"链接
- **页面功能**：
  - 按时间倒序显示最近一周的浏览记录
  - 显示新闻标题、来源、浏览时间
  - 点击标题可跳转到对应新闻详情页
  - 分页显示，每页10条记录

### 3. 数据库表结构
```sql
CREATE TABLE `browse_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_phone` varchar(20) NOT NULL COMMENT '用户手机号',
  `news_id` int(11) NOT NULL COMMENT '新闻ID',
  `browse_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  `deletes` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_news` (`user_phone`, `news_id`),
  KEY `idx_user_phone` (`user_phone`),
  KEY `idx_browse_time` (`browse_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 启动项目

### 后端启动
```bash
cd /Users/xuxue/Documents/11823020317_徐学的副本/Online_education/Community_education
mvn spring-boot:run
```
或者使用Maven打包后运行：
```bash
mvn clean package
cd target
java -jar Community_education-0.0.1-SNAPSHOT.jar
```

### 前端启动
```bash
cd /Users/xuxue/Documents/11823020317_徐学的副本/Online_education/face_education
npm install
npm run serve
```

## 功能验证步骤

1. **数据库准备**：
   - 确保MySQL数据库已启动
   - 执行提供的SQL文件创建`browse_history`表：
   ```bash
   mysql -u root -p education < browse_history.sql
   ```

2. **登录系统**：
   - 访问前端页面（通常是 http://localhost:8080）
   - 使用已有账号登录，或注册新账号

3. **浏览新闻**：
   - 进入"新闻公告"模块
   - 点击任意新闻标题查看详情
   - 系统会自动记录浏览历史（仅对登录用户）

4. **查看历史记录**：
   - 在导航栏点击"历史浏览"
   - 可以看到刚才浏览的新闻记录
   - 记录按时间倒序排列，显示标题、来源、浏览时间
   - 点击标题可以重新查看该新闻

5. **重复浏览测试**：
   - 重复点击同一篇新闻
   - 在历史记录中该新闻的浏览时间应该更新为最新时间

6. **记录清理测试**：
   - 系统会自动清理一周前的浏览记录
   - 超过7天的记录将不再显示

## 技术特点

- **非侵入式设计**：浏览记录功能不会影响原有业务流程
- **异步处理**：记录浏览历史是异步操作，失败不影响新闻查看
- **性能优化**：使用数据库索引和合理的查询优化
- **用户体验**：界面风格与原有系统保持一致

## 注意事项

- 只有登录用户才能使用历史浏览功能
- 浏览记录只保留最近一周的数据
- 同一用户重复浏览同一篇新闻会更新时间戳而不是重复记录
- 系统会自动清理过期记录，无需手动维护