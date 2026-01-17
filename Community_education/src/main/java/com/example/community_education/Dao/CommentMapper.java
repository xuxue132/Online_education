package com.example.community_education.Dao;

import com.example.community_education.Model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectByNewsId(Integer newsId);

    Comment selectById(Integer id);

    int insert(Comment comment);

    int deleteById(@Param("id") Integer id, @Param("userId") Integer userId);

    int selectCountByNewsId(Integer newsId);
}