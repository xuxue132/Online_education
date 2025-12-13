package com.example.community_education.Controller;

import com.example.community_education.Service.impl.LoginServiceImpl;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.encryption.MDFive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/api")
public class LoginController {
    @Resource
    LoginServiceImpl loginService;


    /**
     * 登录
     * */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return loginService.loginLand(map);
    }


    /**
     * 注册
     * */
    @RequestMapping(value = "/register",method = RequestMethod.POST)

    public Result register(@RequestBody Map<String,Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return loginService.register(map);
    }

    /**
     * 验证码
     * */
    @RequestMapping(value = "/validCode")
    public String checkNumber() throws ScriptException, UnsupportedEncodingException, NoSuchAlgorithmException, JsonProcessingException {
        String number = "123456789";
        String icon = "加减乘";
        String icon2 = "+-*";
        String equation = "";
        int result;
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        while (true) {
            int value = (int) (Math.random() * 9 + 0);
            int value2 = (int) (Math.random() * 9 + 0);
            int value3 = (int) (Math.random() * 3 + 0);
            equation = number.charAt(value) + "" + icon.charAt(value3) + "" + number.charAt(value2) + "=";
            result = (int) jse.eval(Integer.parseInt(String.valueOf(number.charAt(value))) + "" + icon2.charAt(value3)
                    + "" + Integer.parseInt(String.valueOf(number.charAt(value2))));
            if (result > 0) {
                break;
            }

        }
        HashMap<String,String> hs = new HashMap<>();
        hs.put("code",equation);
        MDFive mdFive = new MDFive();
        hs.put("answerPlus",mdFive.EncoderByMd5(result+""));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

}
