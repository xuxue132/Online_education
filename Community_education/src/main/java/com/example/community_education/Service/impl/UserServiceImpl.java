package com.example.community_education.Service.impl;

import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Dao.UserRoleMapper;
import com.example.community_education.Model.UserInf;
import com.example.community_education.Service.UserService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import com.example.community_education.Tool.encryption.MDFive;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;
    @Override
    public Result UserInformation(Map<String, Object> map) {
        String phone = TokenUtil.getUsername((String) map.get("token"));
        return ResultTool.success(userMapper.selectPeopleByThePhone(phone));
    }

    public Result updateUserInformation(Map<String, Object> map) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式

        userMapper.updateByPhone(map);
        return ResultTool.success("修改成功!!");
    }

    @Override
    public Result updateUserPhone(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserInf userInf = userMapper.selectPeopleByNewPhone(map);
        UserInf userInf1 = userMapper.selectPeopleByPhone(map);
        if (userInf != null) {
            return ResultTool.error("手机已被绑定");
        }else {
            if (userInf1.getPassword().equals(MDFive.EncoderByMd5((String) map.get("password")))) {
                userMapper.ChangeTelPhone(map);
                userRoleMapper.ChangeTelPhone(map);
                return ResultTool.success("修改成功");
            } else {
                return ResultTool.error("密码错误");
            }
        }
    }

    @Override
    public Result updateUserPassword(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserInf userInf = userMapper.selectPeopleByPhone(map);
        if (userInf == null) {
            return ResultTool.error("用户不存在");
        }else {
            if (userInf.getDeleted() == 1) {
                return ResultTool.error("用户已停用");
            }

            if (userInf.getPassword().equals(MDFive.EncoderByMd5((String) map.get("password")))) {
                map.put("NewPassword", MDFive.EncoderByMd5((String) map.get("NewPassword")));
                userMapper.ChangePassword(map);
                return ResultTool.success("修改成功");
            } else {
                return ResultTool.error("密码错误");
            }
        }
    }

    @Override
    public Result updateUserEmail(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        map.put("email",map.get("NewEmail"));
        UserInf userInf = userMapper.selectPeopleByEmail(map);
        UserInf userInf2 = userMapper.selectPeopleByPhone(map);

        if (userInf == null) {
            if (userInf2.getPassword().equals(MDFive.EncoderByMd5((String) map.get("password1")))) {
                userMapper.ChangeEmail(map);
                return ResultTool.success("绑定成功");
            } else {
                return ResultTool.error("密码错误");
            }

        }else {
            return ResultTool.error("邮箱已被其他帐号所绑定");
        }

    }

    @Override
    public Result headURl(String uploadFile,String telephone) {
        userMapper.headPicture(uploadFile,telephone);
        return ResultTool.success("上传成功");
    }

    @Override
    public Result AllInformation(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(userMapper.AllUser(map));
    }

    @Override
    public Result DeleteUser(Map<String, Object> map) {
        userMapper.DeleteUser(map);
        userRoleMapper.DeleteUser(map);
        return ResultTool.success("封禁成功!");
    }

    @Override
    public Result UnDeleteUser(Map<String, Object> map) {
        userMapper.UnDeleteUser(map);
        userRoleMapper.UnDeleteUser(map);
        return ResultTool.success("解除封禁成功!");
    }

    @Override
    public Result selectUser(Map<String, Object> map) {
        String username = (String)map.get("username");
        String telephone = (String)map.get("telephone");
        if(username != null && username != ""){
            map.put("username","%"+username+"%");
        }
        if(telephone != null && telephone != ""){
            map.put("telephone","%"+telephone+"%");
        }
        return ResultTool.success(userMapper.selectUser(map));
    }

    @Override
    public Result UserSum(Map<String, Object> map) {
        return ResultTool.success(userMapper.UserSum(map));
    }

    @Override
    public Result ChangePasswordPlus(Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserInf userInf = userMapper.selectPeopleByPhone(map);
        if (userInf == null) {
            return ResultTool.error("用户不存在");
        }else {
            if (userInf.getDeleted() == 1) {
                return ResultTool.error("用户已停用");
            }
                map.put("NewPassword", MDFive.EncoderByMd5((String) map.get("NewPassword")));
                userMapper.ChangePasswordPlus(map);
                return ResultTool.success("修改成功");
        }
    }
}
