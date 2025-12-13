package com.example.community_education.Dao;

import com.example.community_education.Model.File;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FileMapper {

//    int deleteByPrimaryKey(Integer id);
//
//    int insert(File record);
//
//    int insertSelective(File record);
//
//    File selectByPrimaryKey(Integer id);
//
//
//    int updateByPrimaryKeySelective(File record);
//
//    int updateByPrimaryKey(File record);
    List<File> selectFile(Map<String, Object> map);

    int SumFile(Map<String, Object> map);

    List<File> AllFile(Map<String, Object> map);

    void AddFile(Map<String, Object> map);

    void DeleteFileName(Map<String, Object> map);

    void DeleteFileNamePlus(Map<String, Object> map);
}