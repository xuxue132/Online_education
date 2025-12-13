package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface PaperTitleService {
    Result Paper(Map<String, Object> map);

    Result AllTitle(Map<String, Object> map);

    Result AllTitleSum(Map<String, Object> map);

    void Delete(Map<String, Object> map);

    void DeleteTitle(Map<String, Object> map);

    Result UpdateTitle(Map<String, Object> map);

    void AddTitle(Map<String, Object> map);

    Result selectTitle(Map<String, Object> map);
}
