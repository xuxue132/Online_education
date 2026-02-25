package com.example.community_education.Dao;

import java.util.List;
import java.util.Map;

public interface NewsFavoriteMapper {

    int insert(Map<String, Object> map);

    int delete(Map<String, Object> map);

    int checkFavorite(Map<String, Object> map);

    int countFavoriteByNewsId(Map<String, Object> map);

    List<Map<String, Object>> getMyFavorites(Map<String, Object> map);
}
