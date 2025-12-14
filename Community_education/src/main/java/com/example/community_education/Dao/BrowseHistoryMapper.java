package com.example.community_education.Dao;

import com.example.community_education.Model.BrowseHistory;
import com.example.community_education.Model.NewNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BrowseHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(BrowseHistory record);

    BrowseHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrowseHistory record);

    // 根据用户手机号和新闻ID查询记录
    BrowseHistory selectByUserAndNews(@Param("userPhone") String userPhone, @Param("newsId") Integer newsId);

    // 插入或更新浏览记录
    int insertOrUpdate(BrowseHistory record);

    // 获取用户最近一周的浏览记录
    List<BrowseHistory> selectRecentByUser(@Param("userPhone") String userPhone);

    // 清理一周前的记录
    int deleteOldRecords();

    // 获取用户浏览历史数量
    int countByUser(@Param("userPhone") String userPhone);

    // 获取用户浏览历史列表（带分页）
    List<Map<String, Object>> selectUserBrowseHistory(Map<String, Object> map);
}