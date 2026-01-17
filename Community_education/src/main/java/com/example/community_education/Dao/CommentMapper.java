package com.example.community_education.Dao;

import com.example.community_education.Model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByNewsId(@Param("newsId") Integer newsId);

    int deleteById(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Comment record);

    Comment selectById(@Param("id") Integer id);

    List<Comment> selectByParentId(@Param("parentId") Integer parentId);
}