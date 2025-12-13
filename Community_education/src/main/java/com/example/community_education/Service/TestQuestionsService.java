package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface TestQuestionsService {
    Result Question(Map<String, Object> map);

    Result AllQuestion(Map<String, Object> map);

    Result AllQuestionSum(Map<String, Object> map);

    void Delete(Map<String, Object> map);
    void DeleteTiTle(Map<String, Object> map);

    Result UpdateQuestion(Map<String, Object> map);

    void AddOption(Map<String, Object> map);

    Result PaperOptionsPlus(Map<String, Object> map);

    Result selectQuestions(Map<String, Object> map);
}
