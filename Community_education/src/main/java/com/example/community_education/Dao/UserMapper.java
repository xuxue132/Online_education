package com.example.community_education.Dao;

import com.example.community_education.Model.UserInf;
import com.example.community_education.Model.UserInfRole;
import com.example.community_education.Tool.Result;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    UserInf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInf record);

    int updateByPrimaryKey(UserInf record);



    UserInf selectPeopleByPhone(Map<String, Object> map);

    void newUserAdd(Map<String, Object> map);

    UserInfRole selectPeopleByThePhone(String telephone);

    void updateByPhone(Map<String, Object> map);

    void ChangeTelPhone(Map<String, Object> map);
    void ChangePassword(Map<String, Object> map);

    UserInf selectPeopleByEmail(Map<String, Object> map);

    void ChangeEmail(Map<String, Object> map);

    void AddEmail(Map<String, Object> map);

    void headPicture(String uploadFile,String telephone);

    void ChangeIntegral(Map<String, Object> map);

    List<UserInfRole> AllUser(Map<String, Object> map);

    void DeleteUser(Map<String, Object> map);

    void UnDeleteUser(Map<String, Object> map);

    List<UserInfRole> selectUser(Map<String, Object> map);

    int UserSum(Map<String, Object> map);

    void ChangePasswordPlus(Map<String, Object> map);

    UserInf selectPeopleByNewPhone(Map<String, Object> map);
}