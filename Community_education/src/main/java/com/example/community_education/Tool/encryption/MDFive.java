package com.example.community_education.Tool.encryption;

import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密
 * */
public class MDFive {
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        //确定算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加密后的字符串
        String newstr = Base64.getEncoder().encodeToString(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

}
