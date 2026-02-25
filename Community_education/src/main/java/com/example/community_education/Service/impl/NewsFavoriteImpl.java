package com.example.community_education.Service.impl;

import com.example.community_education.Dao.NewsFavoriteMapper;
import com.example.community_education.Model.NewsFavorite;
import com.example.community_education.Service.NewsFavoriteService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsFavoriteImpl implements NewsFavoriteService {

    @Resource
    NewsFavoriteMapper newsFavoriteMapper;

    @Override
    public Result addFavorite(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        Integer newsId = (Integer) map.get("newsId");

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        queryMap.put("newsId", newsId);

        NewsFavorite existing = newsFavoriteMapper.selectByUserAndNews(queryMap);
        if (existing != null) {
            return ResultTool.success("已收藏");
        }

        NewsFavorite favorite = new NewsFavorite();
        favorite.setUserId(userId);
        favorite.setNewsId(newsId);
        favorite.setCreateTime(new Date());
        newsFavoriteMapper.insert(favorite);
        return ResultTool.success("收藏成功");
    }

    @Override
    public Result removeFavorite(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        Integer newsId = (Integer) map.get("newsId");

        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("userId", userId);
        deleteMap.put("newsId", newsId);
        newsFavoriteMapper.deleteByUserAndNews(deleteMap);
        return ResultTool.success("取消收藏成功");
    }

    @Override
    public Result checkFavorite(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        Integer newsId = (Integer) map.get("newsId");

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        queryMap.put("newsId", newsId);

        NewsFavorite favorite = newsFavoriteMapper.selectByUserAndNews(queryMap);
        Map<String, Object> result = new HashMap<>();
        result.put("isFavorite", favorite != null);
        return ResultTool.success(result);
    }

    @Override
    public Result getFavoriteList(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        Integer page = (Integer) map.get("page");
        Integer pageSize = (Integer) map.get("pageSize");

        if (page == null) page = 1;
        if (pageSize == null) pageSize = 10;

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        queryMap.put("offset", (page - 1) * pageSize);
        queryMap.put("pageSize", pageSize);

        List<Map<String, Object>> list = newsFavoriteMapper.selectByUserId(queryMap);
        Integer total = newsFavoriteMapper.countByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return ResultTool.success(result);
    }

    @Override
    public Result getFavoriteCount(Integer newsId) {
        Integer count = newsFavoriteMapper.countByNewsId(newsId);
        return ResultTool.success(count);
    }

    @Override
    public Result getUserFavoriteCount(Integer userId) {
        Integer count = newsFavoriteMapper.countByUserId(userId);
        return ResultTool.success(count);
    }
}
