package com.example.community_education.Service.impl;

import com.example.community_education.Dao.CommentMapper;
import com.example.community_education.Model.Comment;
import com.example.community_education.Service.CommentService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public Result getCommentsByNewsId(Integer newsId) {
        List<Comment> comments = commentMapper.selectByNewsId(newsId);
        return ResultTool.success(comments);
    }

    @Override
    public Result getCommentById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        return ResultTool.success(comment);
    }

    @Override
    public Result addComment(Comment comment) {
        int result = commentMapper.insert(comment);
        if (result > 0) {
            return ResultTool.success("评论成功");
        } else {
            return ResultTool.error("评论失败");
        }
    }

    @Override
    public Result deleteComment(Integer id, Integer userId) {
        int result = commentMapper.deleteById(id, userId);
        if (result > 0) {
            return ResultTool.success("删除成功");
        } else {
            return ResultTool.error("删除失败，您没有权限删除此评论");
        }
    }

    @Override
    public Result getCommentCountByNewsId(Integer newsId) {
        int count = commentMapper.selectCountByNewsId(newsId);
        return ResultTool.success(count);
    }
}
