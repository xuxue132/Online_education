package com.example.community_education.Service;

import com.example.community_education.Model.Comment;
import com.example.community_education.Tool.Result;

public interface CommentService {
    Result getCommentsByNewsId(Integer newsId);

    Result getCommentById(Integer id);

    Result addComment(Comment comment);

    Result deleteComment(Integer id, Integer userId);

    Result getCommentCountByNewsId(Integer newsId);
}
