package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface FileInformationService {
    Result AllFileInformation(Map<String, Object> map);

    Result AnFileInformation(Map<String, Object> map);

    Result SumFileInformation(Map<String, Object> map);

    Result selectFileInformation(Map<String, Object> map);

    void AddFileInformations(Map<String, Object> map);

    void UpdateFileInformations(Map<String, Object> map);

    void DeleteFileName(Map<String, Object> map);

}
