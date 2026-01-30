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
import java.util.*;

@Service
public class NewsCommentServiceImpl implements NewsCommentService {

    @Resource
    private NewsCommentMapper newsCommentMapper;
    
    @Resource
    private UserMapper userMapper;

    @Override
    public Result getCommentsByNewsId(Map<String, Object> map) {
        try {
            Integer newsId = (Integer) map.get("newsId");
            if (newsId == null) {
                return ResultTool.error("新闻ID不能为空");
            }
            
            // 获取所有评论
            List<NewsComment> allComments = newsCommentMapper.selectCommentsByNewsId(newsId);
            
            // 将评论组织成一二级结构
            Map<Integer, List<NewsComment>> replyMap = new HashMap<>();
            List<NewsComment> firstLevelComments = new ArrayList<>();
            
            for (NewsComment comment : allComments) {
                if (comment.getParentId() != null && comment.getParentId() > 0) {
                    // 二级评论
                    if (!replyMap.containsKey(comment.getParentId())) {
                        replyMap.put(comment.getParentId(), new ArrayList<>());
                    }
                    replyMap.get(comment.getParentId()).add(comment);
                } else {
                    // 一级评论
                    firstLevelComments.add(comment);
                }
            }
            
            // 构建返回结果
            List<Map<String, Object>> result = new ArrayList<>();
            for (NewsComment firstLevel : firstLevelComments) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("id", firstLevel.getId());
                commentMap.put("newsId", firstLevel.getNewsId());
                commentMap.put("userId", firstLevel.getUserId());
                commentMap.put("username", firstLevel.getUsername());
                commentMap.put("content", firstLevel.getContent());
                commentMap.put("createTime", firstLevel.getCreateTime());
                
                // 添加回复列表
                List<NewsComment> replies = replyMap.getOrDefault(firstLevel.getId(), new ArrayList<>());
                commentMap.put("replies", replies);
                commentMap.put("replyCount", replies.size());
                
                result.add(commentMap);
            }
            
            return ResultTool.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取评论失败");
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
            
            if (newsId == null || userId == null || content == null || content.trim().isEmpty()) {
                return ResultTool.error("参数不完整");
            }
            
            // 获取用户信息
            UserInf user = userMapper.selectByPrimaryKey(userId);
            if (user == null) {
                return ResultTool.error("用户不存在");
            }
            
            NewsComment comment = new NewsComment();
            comment.setNewsId(newsId);
            comment.setUserId(userId);
            comment.setUsername(user.getUsername());
            comment.setContent(content.trim());
            comment.setCreateTime(new Date());
            comment.setDeleted(0);
            
            // 处理回复评论
            if (parentId != null && parentId > 0) {
                NewsComment parentComment = newsCommentMapper.selectByPrimaryKey(parentId);
                if (parentComment == null) {
                    return ResultTool.error("父评论不存在");
                }
                comment.setParentId(parentId);
                comment.setParentUsername(parentComment.getUsername());
                
                // 如果评论内容没有@用户名，自动添加
                if (!content.contains("@" + parentComment.getUsername())) {
                    comment.setContent("@" + parentComment.getUsername() + " " + content.trim());
                }
            } else {
                comment.setParentId(0);
            }
            
            newsCommentMapper.insertSelective(comment);
            
            Map<String, Object> result = new HashMap<>();
            result.put("id", comment.getId());
            result.put("username", comment.getUsername());
            result.put("content", comment.getContent());
            result.put("createTime", comment.getCreateTime());
            result.put("parentId", comment.getParentId());
            result.put("parentUsername", comment.getParentUsername());
            
            return ResultTool.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("添加评论失败");
        }
    }

    @Override
    @Transactional
    public Result deleteComment(Map<String, Object> map) {
        try {
            Integer commentId = (Integer) map.get("commentId");
            Integer userId = (Integer) map.get("userId");
            
            if (commentId == null || userId == null) {
                return ResultTool.error("参数不完整");
            }
            
            NewsComment comment = newsCommentMapper.selectByPrimaryKey(commentId);
            if (comment == null) {
                return ResultTool.error("评论不存在");
            }
            
            // 只有评论作者可以删除评论
            if (!comment.getUserId().equals(userId)) {
                return ResultTool.error("无权删除此评论");
            }
            
            // 软删除评论
            newsCommentMapper.softDeleteComment(commentId);
            
            return ResultTool.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("删除评论失败");
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
            return ResultTool.success(count);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取评论数失败");
        }
    }
}