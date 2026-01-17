package com.example.community_education.Service.impl;

import com.example.community_education.Dao.CommentMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Model.Comment;
import com.example.community_education.Model.UserInf;
import com.example.community_education.Service.CommentService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public Result addComment(Map<String, Object> map) {
        try {
            String token = (String) map.get("token");
            if (token == null || token.isEmpty()) {
                return ResultTool.error("未登录，请先登录");
            }
            String telephone = TokenUtil.getUsername(token);
            UserInf user = userMapper.selectByTelephone(telephone);
            if (user == null) {
                return ResultTool.error("用户不存在");
            }

            Comment comment = new Comment();
            comment.setNewsId(Integer.parseInt(map.get("newsId").toString()));
            comment.setUserId(user.getId());
            comment.setContent((String) map.get("content"));

            if (map.get("parentId") != null && !map.get("parentId").toString().equals("0")) {
                comment.setParentId(Integer.parseInt(map.get("parentId").toString()));
                if (map.get("replyToId") != null) {
                    comment.setReplyToId(Integer.parseInt(map.get("replyToId").toString()));
                }
            } else {
                comment.setParentId(0);
                comment.setReplyToId(0);
            }

            comment.setCreateTime(new Date());
            comment.setDeletes(0);

            int result = commentMapper.insertSelective(comment);
            if (result > 0) {
                return ResultTool.success("评论成功");
            } else {
                return ResultTool.error("评论失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("系统异常");
        }
    }

    @Override
    public Result getComments(Map<String, Object> map) {
        try {
            Integer newsId = Integer.parseInt(map.get("newsId").toString());
            List<Comment> comments = commentMapper.selectByNewsId(newsId);

            List<Map<String, Object>> commentList = new ArrayList<>();
            for (Comment comment : comments) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("id", comment.getId());
                commentMap.put("userId", comment.getUserId());
                commentMap.put("username", comment.getUsername());
                commentMap.put("content", comment.getContent());
                commentMap.put("parentId", comment.getParentId());
                commentMap.put("createTime", comment.getCreateTime());
                commentMap.put("replyToId", comment.getReplyToId());
                commentMap.put("replyUsername", comment.getReplyUsername());

                List<Comment> replies = commentMapper.selectByParentId(comment.getId());
                List<Map<String, Object>> replyList = new ArrayList<>();
                for (Comment reply : replies) {
                    Map<String, Object> replyMap = new HashMap<>();
                    replyMap.put("id", reply.getId());
                    replyMap.put("userId", reply.getUserId());
                    replyMap.put("username", reply.getUsername());
                    replyMap.put("content", reply.getContent());
                    replyMap.put("parentId", reply.getParentId());
                    replyMap.put("createTime", reply.getCreateTime());
                    replyMap.put("replyToId", reply.getReplyToId());
                    replyMap.put("replyUsername", reply.getReplyUsername());
                    replyList.add(replyMap);
                }
                commentMap.put("replies", replyList);
                commentList.add(commentMap);
            }

            return ResultTool.success(commentList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取评论失败");
        }
    }

    @Override
    public Result deleteComment(Map<String, Object> map) {
        try {
            String token = (String) map.get("token");
            if (token == null || token.isEmpty()) {
                return ResultTool.error("未登录，请先登录");
            }
            String telephone = TokenUtil.getUsername(token);
            UserInf user = userMapper.selectByTelephone(telephone);
            if (user == null) {
                return ResultTool.error("用户不存在");
            }

            Integer commentId = Integer.parseInt(map.get("commentId").toString());
            Comment comment = commentMapper.selectById(commentId);
            if (comment == null) {
                return ResultTool.error("评论不存在");
            }

            if (!comment.getUserId().equals(user.getId())) {
                return ResultTool.error("无权删除该评论");
            }

            int result = commentMapper.deleteById(commentId);
            if (result > 0) {
                return ResultTool.success("删除成功");
            } else {
                return ResultTool.error("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("系统异常");
        }
    }

    @Override
    public Result getReplyComments(Map<String, Object> map) {
        try {
            Integer parentId = Integer.parseInt(map.get("parentId").toString());
            List<Comment> replies = commentMapper.selectByParentId(parentId);

            List<Map<String, Object>> replyList = new ArrayList<>();
            for (Comment reply : replies) {
                Map<String, Object> replyMap = new HashMap<>();
                replyMap.put("id", reply.getId());
                replyMap.put("userId", reply.getUserId());
                replyMap.put("username", reply.getUsername());
                replyMap.put("content", reply.getContent());
                replyMap.put("parentId", reply.getParentId());
                replyMap.put("createTime", reply.getCreateTime());
                replyMap.put("replyToId", reply.getReplyToId());
                replyMap.put("replyUsername", reply.getReplyUsername());
                replyList.add(replyMap);
            }

            return ResultTool.success(replyList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.error("获取回复失败");
        }
    }
}