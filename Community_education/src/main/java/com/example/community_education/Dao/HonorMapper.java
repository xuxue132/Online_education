package com.example.community_education.Dao;

import com.example.community_education.Model.Commodity;
import com.example.community_education.Model.Honor;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HonorMapper {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(Honor record);
//
//    int insertSelective(Honor record);
//
//    Honor selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(Honor record);
//
//    int updateByPrimaryKey(Honor record);

    int AllHonorSum(Map<String, Object> map);

    List<Honor> SelectHonor(Map<String, Object> map);

    Honor SelectAnHonor(Map<String, Object> map);

    List<Honor> selectHonor(Map<String, Object> map);

    void Add(Map<String, Object> map);

    void ChangeHonor(Map<String, Object> map);

    void HonorUpload(Map<String, Object> map);

    void DeleteHonor(Map<String, Object> map);
}