package com.example.community_education.Dao;

import com.example.community_education.Model.NewsComment;

import java.util.List;
import java.util.Map;

public interface NewsCommentMapper {

    List<NewsComment> selectCommentsByNewsId(Map<String, Object> map);

    List<NewsComment> selectRepliesByParentId(Map<String, Object> map);

    int insertComment(Map<String, Object> map);

    void deleteComment(Map<String, Object> map);

    NewsComment selectCommentById(Map<String, Object> map);
}
