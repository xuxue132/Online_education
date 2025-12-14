package com.example.community_education.Service.impl;

import com.example.community_education.Dao.BrowseHistoryMapper;
import com.example.community_education.Model.BrowseHistory;
import com.example.community_education.Service.BrowseHistoryService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrowseHistoryImpl implements BrowseHistoryService {
    
    @Resource
    BrowseHistoryMapper browseHistoryMapper;

    @Override
    public void recordBrowseHistory(Map<String, Object> map) {
        try {
            String token = (String) map.get("token");
            if (token == null || token.isEmpty()) {
                return;
            }
            
            String userPhone = TokenUtil.getUsername(token);
            if (userPhone == null || userPhone.isEmpty()) {
                return;
            }
            
            Integer newsId = (Integer) map.get("newsId");
            if (newsId == null) {
                return;
            }
            
            BrowseHistory history = new BrowseHistory();
            history.setUserPhone(userPhone);
            history.setNewsId(newsId);
            history.setBrowseTime(new Date());
            history.setDeletes(0);
            
            // 使用insertOrUpdate，如果记录已存在则更新时间
            browseHistoryMapper.insertOrUpdate(history);
            
            // 清理一周前的记录
            browseHistoryMapper.deleteOldRecords();
            
        } catch (Exception e) {
            // 记录失败不影响主业务流程
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getUserBrowseHistory(Map<String, Object> map) {
        try {
            String token = (String) map.get("token");
            if (token == null || token.isEmpty()) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("code", 400);
                errorResult.put("message", "用户未登录");
                return errorResult;
            }
            
            String userPhone = TokenUtil.getUsername(token);
            if (userPhone == null || userPhone.isEmpty()) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("code", 400);
                errorResult.put("message", "用户未登录");
                return errorResult;
            }
            
            // 处理分页参数
            Integer nowPage = (Integer) map.get("NowPage");
            if (nowPage == null || nowPage < 1) {
                nowPage = 1;
            }
            map.put("NowPage", (nowPage - 1) * 10);
            map.put("userPhone", userPhone);
            
            List<Map<String, Object>> historyList = browseHistoryMapper.selectUserBrowseHistory(map);
            Map<String, Object> successResult = new HashMap<>();
            successResult.put("code", 200);
            successResult.put("data", historyList);
            return successResult;
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code", 400);
            errorResult.put("message", "获取浏览历史失败");
            return errorResult;
        }
    }

    @Override
    public Map<String, Object> getUserBrowseHistoryCount(Map<String, Object> map) {
        try {
            String token = (String) map.get("token");
            if (token == null || token.isEmpty()) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("code", 400);
                errorResult.put("message", "用户未登录");
                return errorResult;
            }
            
            String userPhone = TokenUtil.getUsername(token);
            if (userPhone == null || userPhone.isEmpty()) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("code", 400);
                errorResult.put("message", "用户未登录");
                return errorResult;
            }
            
            int count = browseHistoryMapper.countByUser(userPhone);
            Map<String, Object> result = new HashMap<>();
            result.put("total", count);
            Map<String, Object> successResult = new HashMap<>();
            successResult.put("code", 200);
            successResult.put("data", result);
            return successResult;
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code", 400);
            errorResult.put("message", "获取浏览历史数量失败");
            return errorResult;
        }
    }
}