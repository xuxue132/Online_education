package com.example.community_education.Dao;

import com.example.community_education.Model.PaperAnswer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PaperAnswerMapper {

//
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(PaperAnswer record);
//
//    int insertSelective(PaperAnswer record);
//
//
//    PaperAnswer selectByPrimaryKey(Integer id);
//
//
//
//    int updateByPrimaryKeySelective(PaperAnswer record);
//
//    int updateByPrimaryKey(PaperAnswer record);

    int insert(Map<String, Object> map);

    List<PaperAnswer> SelectAnswer(Map<String, Object> map);
}