package com.example.community_education.Model;

public class PaperTitle {
    private Integer id;

    private Integer idPaper;

    private String title;

    private String rightKey;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPaper() {
        return idPaper;
    }

    public void setIdPaper(Integer idPaper) {
        this.idPaper = idPaper;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRightKey() {
        return rightKey;
    }

    public void setRightKey(String rightKey) {
        this.rightKey = rightKey == null ? null : rightKey.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}