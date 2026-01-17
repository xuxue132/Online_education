package com.example.community_education.Service;

import com.example.community_education.Model.Comment;
import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    int deleteComment(Integer id);

    List<Comment> getCommentsByNewsId(Integer newsId);

    Comment getCommentById(Integer id);
}
