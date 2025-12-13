package com.example.community_education.Dao;

import com.example.community_education.Model.UserInfRole;
import com.example.community_education.Model.UserRole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    void addUserRole(Map<String, Object> map);

    void ChangeTelPhone(Map<String, Object> map);


    void DeleteUser(Map<String, Object> map);

    void UnDeleteUser(Map<String, Object> map);

}