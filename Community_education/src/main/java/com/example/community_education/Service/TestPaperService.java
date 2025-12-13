package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface TestPaperService {
    Result Papers(Map<String, Object> map);

    Result Number();

    void Delete(Map<String, Object> map);

    void UpdatePaper(Map<String, Object> map);

    void AddPaper(Map<String, Object> map);

    void ChangeEffect(Map<String, Object> map);

    Result selectPaper(Map<String, Object> map);

//    Result AllPaperAndQuestion(Map<String, Object> map);
}
