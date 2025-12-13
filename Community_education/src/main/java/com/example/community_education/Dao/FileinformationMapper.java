package com.example.community_education.Dao;

import com.example.community_education.Model.Fileinformation;

import java.util.List;
import java.util.Map;

import com.example.community_education.Model.Honor;
import com.example.community_education.Tool.Result;
import org.apache.ibatis.annotations.Param;

public interface FileinformationMapper {

//    int deleteByPrimaryKey(Integer id);
//
//    int insert(Fileinformation record);
//
//    int insertSelective(Fileinformation record);
//
//    Fileinformation selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(Fileinformation record);
//
//    int updateByPrimaryKey(Fileinformation record);

    int SumFileInformation(Map<String, Object> map);

    List<Fileinformation> selectFileInformation(Map<String, Object> map);

    Fileinformation selectAnFileInformation(Map<String, Object> map);

    List<Fileinformation> selectFileInformations(Map<String, Object> map);

    void AddFileInformations(Map<String, Object> map);

    void UpdateFileInformations(Map<String, Object> map);

    void DeleteFileName(Map<String, Object> map);
}