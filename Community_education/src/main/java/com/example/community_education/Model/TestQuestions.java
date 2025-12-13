package com.example.community_education.Model;

public class TestQuestions {
    private Integer id;

    private Integer paperId;

    private Integer titleId;

    private String optionS;

    private String optionValue;

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    private Integer deleted;

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getOptionS() {
        return optionS;
    }

    public void setOptionS(String optionS) {
        this.optionS = optionS == null ? null : optionS.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}