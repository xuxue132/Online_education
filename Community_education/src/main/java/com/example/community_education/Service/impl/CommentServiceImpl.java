package com.example.community_education.Service.impl;

import com.example.community_education.Dao.CommentMapper;
import com.example.community_education.Model.Comment;
import com.example.community_education.Service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public int deleteComment(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Comment> getCommentsByNewsId(Integer newsId) {
        return commentMapper.selectCommentsWithUserInfo(newsId);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }
}
