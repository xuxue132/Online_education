package com.example.community_education.Dao;

import com.example.community_education.Model.TestQuestions;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TestQuestionsMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Map<String, Object> map);

    int insertSelective(TestQuestions record);


    TestQuestions selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(TestQuestions record);

    int updateByPrimaryKey(Map<String, Object> map);

    List<TestQuestions> selectByPaperId(Map<String, Object> map);

    List<TestQuestions> AllQuestion(Map<String, Object> map);

    int AllQuestionSum(Map<String, Object> map);

    void Delete(Map<String, Object> map);

    void DeleteTiTle(Map<String, Object> map);

    List<TestQuestions> selectByTitleId(Map<String, Object> map);
}