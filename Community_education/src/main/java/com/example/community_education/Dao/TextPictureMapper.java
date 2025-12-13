package com.example.community_education.Dao;

import com.example.community_education.Model.TestQuestions;
import com.example.community_education.Model.TextPicture;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TextPictureMapper {

    int deleteByPrimaryKey(Integer id);
//
//    int insert(TextPicture record);
//
//    int insertSelective(TextPicture record);
//
//    TextPicture selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(TextPicture record);
//
//    int updateByPrimaryKey(TextPicture record);

    void update(Map<String, Object> map);

    void insert(Map<String, Object> map);

    List<TextPicture> select(Map<String, Object> map);
}