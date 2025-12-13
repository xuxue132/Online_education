package com.example.community_education.Controller.File;


public class QiNiuConfigBean {

    private static String accessKey = "VzBjRWxNI6LOktAdXW8ZSbInG05V95NaSXQsXUjh";
    private static String secretKey = "yVo6cBLcygiSGJZVQXzK29atKbYKO43Wl82NPTBs";
    private static String bucketname = "euducation-web";
    private static String cdnProfile = "eucation.i8329.cn";
    private static String protocol = "http://";

    public static String getAccessKey() {
        return accessKey;
    }

    public  void setAccessKey(String accessKey) {
        QiNiuConfigBean.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public  void setSecretKey(String secretKey) {
        QiNiuConfigBean.secretKey = secretKey;
    }

    public static String getBucket() {
        return bucketname;
    }

    public  void setBucket(String bucketname) {
        QiNiuConfigBean.bucketname = bucketname;
    }

    public static String getCdnProfile() {
        return cdnProfile;
    }

    public  void setCdnProfile(String cdnProfile) {
        QiNiuConfigBean.cdnProfile = cdnProfile;
    }

    public static String getProtocol() {
        return protocol;
    }

    public  void setProtocol(String protocol) {
        QiNiuConfigBean.protocol = protocol;
    }
}

