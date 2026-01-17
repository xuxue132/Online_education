package com.example.community_education.Dao;

import com.example.community_education.Model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insert(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByNewsId(@Param("newsId") Integer newsId);

    Comment selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    List<Comment> selectCommentsWithUserInfo(@Param("newsId") Integer newsId);
}
