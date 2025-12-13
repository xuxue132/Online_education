package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface FileService {
    Result TheFile(Map<String, Object> map);

    Result SumFile(Map<String, Object> map);

    Result AllFile(Map<String, Object> map);

    void AddFile(Map<String, Object> map);

    void DeleteFileName(Map<String, Object> map);

    void DeleteFileNamePlus(Map<String, Object> map);
}
