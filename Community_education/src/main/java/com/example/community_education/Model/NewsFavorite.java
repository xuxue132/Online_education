package com.example.community_education.Model;

import java.util.Date;

public class NewsFavorite {
    private Integer id;

    private Integer userId;

    private Integer newsId;

    private Date createTime;

    // 关联新闻信息（用于查询收藏列表时返回新闻详情）
    private NewNotice newsInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public NewNotice getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(NewNotice newsInfo) {
        this.newsInfo = newsInfo;
    }
}
