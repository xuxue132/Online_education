package com.example.community_education.Service.impl;

import com.example.community_education.Dao.NewsFavoriteMapper;
import com.example.community_education.Service.NewsFavoriteService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class NewsFavoriteImpl implements NewsFavoriteService {
    @Resource
    NewsFavoriteMapper newsFavoriteMapper;

    @Override
    public Result addFavorite(Map<String, Object> map) {
        map.put("createTime", new Date());
        newsFavoriteMapper.insert(map);
        return ResultTool.success("收藏成功");
    }

    @Override
    public Result removeFavorite(Map<String, Object> map) {
        newsFavoriteMapper.delete(map);
        return ResultTool.success("取消收藏成功");
    }

    @Override
    public Result checkFavorite(Map<String, Object> map) {
        int count = newsFavoriteMapper.checkFavorite(map);
        return ResultTool.success(count);
    }

    @Override
    public Result countFavoriteByNewsId(Map<String, Object> map) {
        return ResultTool.success(newsFavoriteMapper.countFavoriteByNewsId(map));
    }

    @Override
    public Result getMyFavorites(Map<String, Object> map) {
        return ResultTool.success(newsFavoriteMapper.getMyFavorites(map));
    }
}
