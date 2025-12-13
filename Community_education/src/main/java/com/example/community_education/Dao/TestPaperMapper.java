package com.example.community_education.Dao;

import com.example.community_education.Model.TestPaper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TestPaperMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(TestPaper record);

    int insertSelective(TestPaper record);


    TestPaper selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(TestPaper record);

    int updateByPrimaryKey(Map<String, Object> map);

    List<TestPaper> AllPaper(Map<String, Object> map);

    int PaperSum();

    void Delete(Map<String, Object> map);

    void AddPaper(Map<String, Object> map);

    void ChangeEffect(Map<String, Object> map);

    List<TestPaper> selectPaper(Map<String, Object> map);


//    List<Object>  AllPaperAndQuestion();

}