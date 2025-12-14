package com.example.community_education.Model;

import java.util.Date;

public class BrowseHistory {
    private Integer id;
    private String userPhone;
    private Integer newsId;
    private Date browseTime;
    private Integer deletes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }

    public Integer getDeletes() {
        return deletes;
    }

    public void setDeletes(Integer deletes) {
        this.deletes = deletes;
    }
}