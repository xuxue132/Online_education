package com.example.community_education.Dao;

import com.example.community_education.Model.NewsFavorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewsFavoriteMapper {

    /**
     * 添加收藏
     */
    int insert(NewsFavorite record);

    /**
     * 删除收藏
     */
    int deleteByUserAndNews(@Param("userId") Integer userId, @Param("newsId") Integer newsId);

    /**
     * 查询用户是否已收藏某新闻
     */
    NewsFavorite selectByUserAndNews(@Param("userId") Integer userId, @Param("newsId") Integer newsId);

    /**
     * 查询用户的收藏列表
     */
    List<NewsFavorite> selectByUserId(@Param("userId") Integer userId);

    /**
     * 查询用户的收藏列表（分页）
     */
    List<NewsFavorite> selectByUserIdWithPage(Map<String, Object> map);

    /**
     * 查询用户收藏总数
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 查询新闻被收藏的次数
     */
    int countByNewsId(@Param("newsId") Integer newsId);

    /**
     * 批量查询多个新闻的收藏数
     */
    List<Map<String, Object>> countByNewsIds(@Param("newsIds") List<Integer> newsIds);
}
