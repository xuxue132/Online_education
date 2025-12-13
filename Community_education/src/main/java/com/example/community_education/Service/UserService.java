package com.example.community_education.Service;

import com.example.community_education.Model.UserInf;
import com.example.community_education.Tool.Result;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UserService {
    Result UserInformation(Map<String, Object> map);
    Result updateUserInformation(Map<String, Object> map);

    Result updateUserPhone(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Result updateUserPassword(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Result updateUserEmail(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Result headURl(String uploadFile,String telephone);

    Result AllInformation(Map<String, Object> map);

    Result DeleteUser(Map<String, Object> map);

    Result UnDeleteUser(Map<String, Object> map);

    Result selectUser(Map<String, Object> map);

    Result UserSum(Map<String, Object> map);

    Result ChangePasswordPlus(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
