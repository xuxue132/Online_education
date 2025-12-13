package com.example.community_education.Dao;

import com.example.community_education.Model.PaperTitle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PaperTitleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Map<String, Object> map);

    int insertSelective(PaperTitle record);


    PaperTitle selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(PaperTitle record);

    int updateByPrimaryKey(Map<String, Object> map);

    List<PaperTitle> selectTitle(Map<String, Object> map);

    List<PaperTitle> AllTitle(Map<String, Object> map);

    int AllTitleSum(Map<String, Object> map);

    void Delete(Map<String, Object> map);

    void DeleteTitle(Map<String, Object> map);

    List<PaperTitle> selectTitleS(Map<String, Object> map);
}