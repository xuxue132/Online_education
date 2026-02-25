package com.example.community_education.Dao;

import com.example.community_education.Model.NewsFavorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewsFavoriteMapper {

    int insert(NewsFavorite record);

    int deleteByUserAndNews(Map<String, Object> map);

    NewsFavorite selectByUserAndNews(Map<String, Object> map);

    List<Map<String, Object>> selectByUserId(Map<String, Object> map);

    int countByNewsId(Integer newsId);

    int countByUserId(Integer userId);
}
