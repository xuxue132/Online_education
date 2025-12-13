package com.example.community_education.Service.impl;

import com.example.community_education.Dao.TestQuestionsMapper;
import com.example.community_education.Service.TestQuestionsService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TestQuestionsImpl implements TestQuestionsService {
    @Resource
    TestQuestionsMapper testQuestionsMapper;

    @Override
    public Result Question(Map<String, Object> map) {
        return ResultTool.success(testQuestionsMapper.selectByPaperId(map));
    }

    @Override
    public Result AllQuestion(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(testQuestionsMapper.AllQuestion(map));
    }

    @Override
    public Result AllQuestionSum(Map<String, Object> map) {
        return ResultTool.success(testQuestionsMapper.AllQuestionSum(map));
    }

    @Override
    public void Delete(Map<String, Object> map) {
        testQuestionsMapper.Delete(map);
    }

    @Override
    public void DeleteTiTle(Map<String, Object> map) {
        testQuestionsMapper.DeleteTiTle(map);
    }

    @Override
    public Result UpdateQuestion(Map<String, Object> map) {
        testQuestionsMapper.updateByPrimaryKey(map);
        return ResultTool.success("修改成功");
    }

    @Override
    public void AddOption(Map<String, Object> map) {
        testQuestionsMapper.insert(map);
    }

    @Override
    public Result PaperOptionsPlus(Map<String, Object> map) {
        return ResultTool.success(testQuestionsMapper.selectByTitleId(map));
    }

    @Override
    public Result selectQuestions(Map<String, Object> map) {
        return ResultTool.success(testQuestionsMapper.selectByTitleId(map));
    }
}
