package com.example.community_education.Model;

import java.util.Date;

public class NewsComment {
    private Integer id;
    private Integer newsId;          // 新闻ID
    private Integer userId;          // 用户ID
    private String username;         // 用户名（冗余存储，便于显示）
    private Integer parentId;        // 父评论ID，0表示一级评论
    private String parentUsername;   // 被回复的用户名，用于@功能
    private String content;          // 评论内容
    private Date createTime;         // 创建时间
    private Integer deleted;         // 删除标记，0表示未删除，1表示已删除

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentUsername() {
        return parentUsername;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername == null ? null : parentUsername.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}