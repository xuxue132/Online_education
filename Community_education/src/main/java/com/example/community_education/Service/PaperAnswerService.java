package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface PaperAnswerService {
    Result Answer(Map<String, Object> map);

    Result AnswerInsert(Map<String, Object> map);

    Result AnswerSorce(Map<String, Object> map);
}
