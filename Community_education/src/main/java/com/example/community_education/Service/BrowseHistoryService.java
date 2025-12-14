package com.example.community_education.Service;

import java.util.Map;

public interface BrowseHistoryService {
    // 记录用户浏览历史
    void recordBrowseHistory(Map<String, Object> map);
    
    // 获取用户浏览历史列表
    Map<String, Object> getUserBrowseHistory(Map<String, Object> map);
    
    // 获取用户浏览历史数量
    Map<String, Object> getUserBrowseHistoryCount(Map<String, Object> map);
}