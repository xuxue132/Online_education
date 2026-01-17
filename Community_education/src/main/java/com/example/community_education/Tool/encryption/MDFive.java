package com.example.community_education.Tool.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 加密
 * */
public class MDFive {
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        //确定算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder base64en = Base64.getEncoder();
        //加密后的字符串
        String newstr = base64en.encodeToString(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

}
