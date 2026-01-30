package com.example.community_education.Service;

import com.example.community_education.Tool.Result;
import java.util.Map;

public interface NewsCommentService {
    /**
     * 获取新闻的所有评论
     */
    Result getCommentsByNewsId(Map<String, Object> map);
    
    /**
     * 添加评论
     */
    Result addComment(Map<String, Object> map);
    
    /**
     * 删除评论
     */
    Result deleteComment(Map<String, Object> map);
    
    /**
     * 获取用户的所有评论
     */
    Result getUserComments(Map<String, Object> map);
    
    /**
     * 获取新闻的评论总数
     */
    Result getCommentCount(Map<String, Object> map);
}