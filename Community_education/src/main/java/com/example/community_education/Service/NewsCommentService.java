package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface NewsCommentService {
    Result getCommentsByNewsId(Map<String, Object> map);

    Result addComment(Map<String, Object> map);

    Result deleteComment(Map<String, Object> map);
}
