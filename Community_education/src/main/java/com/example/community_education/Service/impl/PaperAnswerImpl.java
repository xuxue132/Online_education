package com.example.community_education.Service.impl;

import com.example.community_education.Dao.PaperAnswerMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Service.PaperAnswerService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class PaperAnswerImpl implements PaperAnswerService {
    @Resource
    PaperAnswerMapper paperAnswerMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public Result Answer(Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        return ResultTool.success(paperAnswerMapper.SelectAnswer(map));
    }

    @Override
    public Result AnswerInsert(Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        return ResultTool.success(paperAnswerMapper.insert(map));
    }

    @Override
    public Result AnswerSorce(Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        userMapper.ChangeIntegral(map);
        return ResultTool.success("成功");
    }
}
