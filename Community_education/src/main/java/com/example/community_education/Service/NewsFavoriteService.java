package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface NewsFavoriteService {
    Result addFavorite(Map<String, Object> map);

    Result removeFavorite(Map<String, Object> map);

    Result checkFavorite(Map<String, Object> map);

    Result getFavoriteList(Map<String, Object> map);

    Result getFavoriteCount(Integer newsId);

    Result getUserFavoriteCount(Integer userId);
}
