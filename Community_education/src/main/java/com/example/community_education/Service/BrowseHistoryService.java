package com.example.community_education.Service;

import com.example.community_education.Model.BrowseHistory;
import com.example.community_education.Tool.Result;

import java.util.Map;

public interface BrowseHistoryService {
    Result addBrowseHistory(Map<String, Object> map);
    Result getBrowseHistory(Map<String, Object> map);
}