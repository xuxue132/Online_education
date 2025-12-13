package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface LoginService   {
    //登陆
    Result loginLand(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    //注册
    Result register(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
