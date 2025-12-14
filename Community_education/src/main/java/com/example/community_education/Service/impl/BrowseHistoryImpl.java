package com.example.community_education.Service.impl;

import com.example.community_education.Dao.BrowseHistoryMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Model.BrowseHistory;
import com.example.community_education.Model.UserInfRole;
import com.example.community_education.Service.BrowseHistoryService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class BrowseHistoryImpl implements BrowseHistoryService {
    @Resource
    private BrowseHistoryMapper browseHistoryMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Result addBrowseHistory(Map<String, Object> map) {
        String telephone = TokenUtil.getUsername((String) map.get("token"));
        UserInfRole userInfRole = userMapper.selectPeopleByThePhone(telephone);
        Integer userId = userInfRole.getId();
        Integer newsId = (Integer) map.get("newsId");
        Date browseTime = new Date();

        BrowseHistory existingHistory = browseHistoryMapper.selectByUserIdAndNewsId(userId, newsId);
        if (existingHistory != null) {
            browseHistoryMapper.updateBrowseTime(userId, newsId, browseTime);
        } else {
            BrowseHistory browseHistory = new BrowseHistory();
            browseHistory.setUserId(userId);
            browseHistory.setNewsId(newsId);
            browseHistory.setBrowseTime(browseTime);
            browseHistoryMapper.insert(browseHistory);
        }

        return ResultTool.success("记录成功");
    }

    @Override
    public Result getBrowseHistory(Map<String, Object> map) {
        String telephone = TokenUtil.getUsername((String) map.get("token"));
        UserInfRole userInfRole = userMapper.selectPeopleByThePhone(telephone);
        Integer userId = userInfRole.getId();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date oneWeekAgo = calendar.getTime();

        return ResultTool.success(browseHistoryMapper.selectByUserId(userId, oneWeekAgo));
    }
}