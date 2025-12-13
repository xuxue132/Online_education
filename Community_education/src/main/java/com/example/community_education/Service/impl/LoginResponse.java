package com.example.community_education.Service.impl;

import com.example.community_education.Model.UserInf;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  登录请求接口返回信息
 */
@Data
public class LoginResponse {
    @JsonProperty("token")
    private String token;
    @JsonProperty("user")
    private UserInf userInf;

    @JsonProperty("identity")
    private String identity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInf getUserInf() {
        return userInf;
    }

    public void setUserInf(UserInf userInf) {
        this.userInf = userInf;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
