package com.example.community_education.Dao;

import com.example.community_education.Model.BrowseHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface BrowseHistoryMapper {
    int insert(BrowseHistory record);
    int updateBrowseTime(@Param("userId") Integer userId, @Param("newsId") Integer newsId, @Param("browseTime") Date browseTime);
    BrowseHistory selectByUserIdAndNewsId(@Param("userId") Integer userId, @Param("newsId") Integer newsId);
    List<Map<String, Object>> selectByUserId(@Param("userId") Integer userId, @Param("startTime") Date startTime);
    int deleteByUserId(@Param("userId") Integer userId);
}