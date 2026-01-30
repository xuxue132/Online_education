package com.example.community_education.Service.impl;

import com.example.community_education.Dao.NewsCommentMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Model.NewsComment;
import com.example.community_education.Model.UserInf;
import com.example.community_education.Service.NewsCommentService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsCommentImpl implements NewsCommentService {
    @Resource
    NewsCommentMapper newsCommentMapper;
    
    @Resource
    UserMapper userMapper;

    @Override
    public Result getCommentsByNewsId(Map<String, Object> map) {
        try {
            Integer newsId = (Integer) map.get("newsId");
            if (newsId == null) {
                return ResultTool.error("新闻ID不能为空");
            }

            List<NewsComment> comments = newsCommentMapper.selectCommentsByNewsId(newsId);
            return ResultTool.success(comments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取评论列表失败");
        }
    }

    @Override
    @Transactional
    public Result addComment(Map<String, Object> map) {
        try {
            Integer newsId = (Integer) map.get("newsId");
            Integer userId = (Integer) map.get("userId");
            String content = (String) map.get("content");
            Integer parentId = (Integer) map.get("parentId");
            String parentUsername = (String) map.get("parentUsername");

            if (newsId == null || userId == null || content == null || content.trim().isEmpty()) {
                return ResultTool.error("参数不完整");
            }

            // 获取用户名（这里假设从UserService获取，实际可能需要调用其他服务）
            String username = getUsernameById(userId);

            NewsComment comment = new NewsComment();
            comment.setNewsId(newsId);
            comment.setUserId(userId);
            comment.setUsername(username);
            comment.setParentId(parentId != null ? parentId : 0);
            comment.setParentUsername(parentUsername);
            comment.setContent(content);
            comment.setCreateTime(new Date());
            comment.setDeleted(0);

            int result = newsCommentMapper.insert(comment);
            if (result > 0) {
                return ResultTool.success("评论发表成功");
            } else {
                return ResultTool.error("评论发表失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("评论发表失败");
        }
    }

    @Override
    @Transactional
    public Result deleteComment(Map<String, Object> map) {
        try {
            Integer commentId = (Integer) map.get("id");
            Integer userId = (Integer) map.get("userId");

            if (commentId == null || userId == null) {
                return ResultTool.error("参数不完整");
            }

            // 检查评论是否存在且属于当前用户
            NewsComment comment = newsCommentMapper.selectByPrimaryKey(commentId);
            if (comment == null) {
                return ResultTool.error("评论不存在");
            }

            if (!comment.getUserId().equals(userId)) {
                return ResultTool.error("无权删除此评论");
            }

            // 软删除评论
            int result = newsCommentMapper.softDeleteComment(commentId);
            if (result > 0) {
                return ResultTool.success("删除成功");
            } else {
                return ResultTool.error("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("删除失败");
        }
    }

    @Override
    public Result getUserComments(Map<String, Object> map) {
        try {
            Integer userId = (Integer) map.get("userId");
            if (userId == null) {
                return ResultTool.error("用户ID不能为空");
            }

            List<NewsComment> comments = newsCommentMapper.selectCommentsByUserId(userId);
            return ResultTool.success(comments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取用户评论失败");
        }
    }

    @Override
    public Result getCommentCount(Map<String, Object> map) {
        try {
            Integer newsId = (Integer) map.get("newsId");
            if (newsId == null) {
                return ResultTool.error("新闻ID不能为空");
            }

            int count = newsCommentMapper.countCommentsByNewsId(newsId);
            Map<String, Object> data = new HashMap<>();
            data.put("count", count);
            return ResultTool.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取评论数量失败");
        }
    }

    /**
     * 根据用户ID获取用户名
     * 这里需要根据实际情况实现，可能需要调用UserService
     */
    private String getUsernameById(Integer userId) {
        try {
            UserInf user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                return user.getUsername();
            }
            return "用户" + userId;
        } catch (Exception e) {
            e.printStackTrace();
            return "用户" + userId;
        }
    }
}