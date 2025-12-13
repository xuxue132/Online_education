package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface HonorService {
    Result AllHonorSum(Map<String, Object> map);

    Result AllHonor(Map<String, Object> map);

    Result AnHonor(Map<String, Object> map);

    void DeleteHonor(Map<String, Object> map);

    void HonorUpload(Map<String, Object> map);

    void ChangeHonor(Map<String, Object> map);

    void AddHonor(Map<String, Object> map);

    Result selectHonor(Map<String, Object> map);
}
