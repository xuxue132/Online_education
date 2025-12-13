package com.example.community_education.Dao;

import com.example.community_education.Model.CharacterProject;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CharacterProjectMapper {
    List<CharacterProject> AllProject(Map<String, Object> map);

    CharacterProject AnProject(Map<String, Object> map);

    int ProjectNumber();

    int insert(Map<String, Object> map);

    List<CharacterProject> selectCharacterProject(Map<String, Object> map);

    void updateImages(Map<String, Object> map);

    void update(Map<String, Object> map);

    void delete(Map<String, Object> map);

//    int countByExample(CharacterProjectExample example);
//
//    int deleteByExample(CharacterProjectExample example);
//
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(CharacterProject record);
//
//    int insertSelective(CharacterProject record);
//
//    List<CharacterProject> selectByExample(CharacterProjectExample example);
//
//    CharacterProject selectByPrimaryKey(Integer id);
//
//    int updateByExampleSelective(@Param("record") CharacterProject record, @Param("example") CharacterProjectExample example);
//
//    int updateByExample(@Param("record") CharacterProject record, @Param("example") CharacterProjectExample example);
//
//    int updateByPrimaryKeySelective(CharacterProject record);
//
//    int updateByPrimaryKey(CharacterProject record);
}