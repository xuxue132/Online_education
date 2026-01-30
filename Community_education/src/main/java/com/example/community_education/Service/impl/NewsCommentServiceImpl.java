package com.example.community_education.Service.impl;

import com.example.community_education.Dao.NewsCommentMapper;
import com.example.community_education.Model.NewsComment;
import com.example.community_education.Service.NewsCommentService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class NewsCommentServiceImpl implements NewsCommentService {
    @Resource
    NewsCommentMapper newsCommentMapper;

    @Override
    public Result getCommentsByNewsId(Map<String, Object> map) {
        List<NewsComment> comments = newsCommentMapper.selectCommentsByNewsId(map);
        List<Map<String, Object>> result = new ArrayList<>();

        for (NewsComment comment : comments) {
            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("id", comment.getId());
            commentMap.put("newsId", comment.getNewsId());
            commentMap.put("userId", comment.getUserId());
            commentMap.put("content", comment.getContent());
            commentMap.put("username", comment.getUsername());
            commentMap.put("createdAt", comment.getCreatedAt());

            Map<String, Object> replyMap = new HashMap<>();
            replyMap.put("parentId", comment.getId());
            List<NewsComment> replies = newsCommentMapper.selectRepliesByParentId(replyMap);

            List<Map<String, Object>> replyList = new ArrayList<>();
            for (NewsComment reply : replies) {
                Map<String, Object> rMap = new HashMap<>();
                rMap.put("id", reply.getId());
                rMap.put("userId", reply.getUserId());
                rMap.put("content", reply.getContent());
                rMap.put("username", reply.getUsername());
                rMap.put("replyUsername", reply.getReplyUsername());
                rMap.put("createdAt", reply.getCreatedAt());
                replyList.add(rMap);
            }
            commentMap.put("replies", replyList);
            result.add(commentMap);
        }
        return ResultTool.success(result);
    }

    @Override
    public Result addComment(Map<String, Object> map) {
        map.put("createdAt", new Date());
        newsCommentMapper.insertComment(map);
        return ResultTool.success("评论成功");
    }

    @Override
    public Result deleteComment(Map<String, Object> map) {
        newsCommentMapper.deleteComment(map);
        return ResultTool.success("删除成功");
    }
}
