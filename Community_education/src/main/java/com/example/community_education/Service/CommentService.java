package com.example.community_education.Service;

import com.example.community_education.Model.Comment;
import com.example.community_education.Tool.Result;

import java.util.List;
import java.util.Map;

public interface CommentService {
    Result addComment(Map<String, Object> map);

    Result getComments(Map<String, Object> map);

    Result deleteComment(Map<String, Object> map);

    Result getReplyComments(Map<String, Object> map);
}