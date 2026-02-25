package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface NewsFavoriteService {

    /**
     * 添加收藏
     */
    Result addFavorite(Map<String, Object> map);

    /**
     * 取消收藏
     */
    Result cancelFavorite(Map<String, Object> map);

    /**
     * 查询用户是否已收藏某新闻
     */
    Result checkFavoriteStatus(Map<String, Object> map);

    /**
     * 获取用户的收藏列表
     */
    Result getUserFavorites(Map<String, Object> map);

    /**
     * 获取用户收藏总数
     */
    Result getUserFavoriteCount(Map<String, Object> map);

    /**
     * 获取新闻被收藏的次数
     */
    Result getNewsFavoriteCount(Map<String, Object> map);
}
