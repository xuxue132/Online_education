package com.example.community_education.Service.impl;

import com.example.community_education.Dao.RolePermissionMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Dao.UserRoleMapper;
import com.example.community_education.Model.RolePermission;
import com.example.community_education.Model.UserInf;
import com.example.community_education.Service.LoginService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import com.example.community_education.Tool.encryption.MDFive;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    UserMapper userMapper;
    @Resource
    RolePermissionMapper rolePermissionMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    public LoginResponse setLoginResponse(UserInf userInf) {
        LoginResponse response = new LoginResponse();
        RolePermission rolePermission = rolePermissionMapper.selectStatus(userInf.getTelephone());
        response.setToken(TokenUtil.sign(rolePermission.getPermissionStatus(), userInf.getTelephone()));
        response.setIdentity(rolePermission.getPermissionStatus());
        response.setUserInf(userInf);
        return response;
    }


    public Result loginLand(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserInf userInf;
        userInf = userMapper.selectPeopleByPhone(map);

        if (userInf == null) {
            UserInf userInf1 = userMapper.selectPeopleByEmail(map);
            if(userInf1 == null) {
                return ResultTool.error("用户不存在");
            }else {
                if (userInf1.getPassword().equals(MDFive.EncoderByMd5((String) map.get("password")))) {
                    return ResultTool.success(setLoginResponse(userInf1), "登陆成功");
                } else {
                    return ResultTool.error("密码错误");
                }
            }
        }else {
            if (userInf.getDeleted() == 1) {
                return ResultTool.error("用户已停用");
            }

            if (userInf.getPassword().equals(MDFive.EncoderByMd5((String) map.get("password")))) {
                return ResultTool.success(setLoginResponse(userInf), "登陆成功");
            } else {
                return ResultTool.error("密码错误");
            }
        }
    }

    public Result register(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(!MDFive.EncoderByMd5((String) map.get("answer")).equals(map.get("answerPlus"))){
            return ResultTool.error("验证码错误");
        }
        UserInf userInf = userMapper.selectPeopleByPhone(map);
        if(userInf == null) {
            map.put("password", MDFive.EncoderByMd5((String) map.get("password")));
            map.put("picture", "\n" +
                    "http://eucation.i8329.cn/initial_head.png");
            map.put("createTime", new Timestamp(new Date().getTime()));
            map.put("roleName","用户");
            userMapper.newUserAdd(map);
            userRoleMapper.addUserRole(map);

            return ResultTool.success("新增成功");
        }else {
            return ResultTool.error("用户已存在");
        }
    }
}
