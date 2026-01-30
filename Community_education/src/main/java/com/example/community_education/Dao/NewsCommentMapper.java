package com.example.community_education.Dao;

import com.example.community_education.Model.NewsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewsCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewsComment record);

    int insertSelective(NewsComment record);

    NewsComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewsComment record);

    int updateByPrimaryKey(NewsComment record);

    /**
     * 根据新闻ID获取所有评论（包括一级评论和二级评论）
     */
    List<NewsComment> selectCommentsByNewsId(@Param("newsId") Integer newsId);

    /**
     * 根据父评论ID获取所有子评论
     */
    List<NewsComment> selectRepliesByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据用户ID获取用户的所有评论
     */
    List<NewsComment> selectCommentsByUserId(@Param("userId") Integer userId);

    /**
     * 软删除评论
     */
    int softDeleteComment(@Param("id") Integer id);

    /**
     * 获取新闻的评论总数
     */
    int countCommentsByNewsId(@Param("newsId") Integer newsId);
}