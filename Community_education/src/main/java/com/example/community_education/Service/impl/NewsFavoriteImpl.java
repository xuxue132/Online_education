package com.example.community_education.Service.impl;

import com.example.community_education.Dao.NewsFavoriteMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Model.NewsFavorite;
import com.example.community_education.Model.UserInf;
import com.example.community_education.Service.NewsFavoriteService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsFavoriteImpl implements NewsFavoriteService {

    @Resource
    NewsFavoriteMapper newsFavoriteMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public Result addFavorite(Map<String, Object> map) {
        Integer userId = getUserIdFromToken(map);
        if (userId == null) {
            return ResultTool.error("用户未登录");
        }

        Integer newsId = Integer.parseInt(map.get("newsId").toString());

        // 检查是否已收藏
        NewsFavorite existing = newsFavoriteMapper.selectByUserAndNews(userId, newsId);
        if (existing != null) {
            return ResultTool.error("已经收藏过了");
        }

        NewsFavorite favorite = new NewsFavorite();
        favorite.setUserId(userId);
        favorite.setNewsId(newsId);

        int result = newsFavoriteMapper.insert(favorite);
        if (result > 0) {
            return ResultTool.success("收藏成功");
        } else {
            return ResultTool.error("收藏失败");
        }
    }

    @Override
    public Result cancelFavorite(Map<String, Object> map) {
        Integer userId = getUserIdFromToken(map);
        if (userId == null) {
            return ResultTool.error("用户未登录");
        }

        Integer newsId = Integer.parseInt(map.get("newsId").toString());

        int result = newsFavoriteMapper.deleteByUserAndNews(userId, newsId);
        if (result > 0) {
            return ResultTool.success("取消收藏成功");
        } else {
            return ResultTool.error("取消收藏失败");
        }
    }

    @Override
    public Result checkFavoriteStatus(Map<String, Object> map) {
        Integer userId = getUserIdFromToken(map);
        if (userId == null) {
            return ResultTool.success(false);
        }

        Integer newsId = Integer.parseInt(map.get("newsId").toString());
        NewsFavorite favorite = newsFavoriteMapper.selectByUserAndNews(userId, newsId);

        Map<String, Object> result = new HashMap<>();
        result.put("isFavorited", favorite != null);
        if (favorite != null) {
            result.put("favoriteTime", favorite.getCreateTime());
        }

        // 同时返回收藏数量
        int count = newsFavoriteMapper.countByNewsId(newsId);
        result.put("favoriteCount", count);

        return ResultTool.success(result);
    }

    @Override
    public Result getUserFavorites(Map<String, Object> map) {
        Integer userId = getUserIdFromToken(map);
        if (userId == null) {
            return ResultTool.error("用户未登录");
        }

        // 分页参数
        int page = 1;
        int pageSize = 10;
        if (map.get("page") != null) {
            page = Integer.parseInt(map.get("page").toString());
        }
        if (map.get("pageSize") != null) {
            pageSize = Integer.parseInt(map.get("pageSize").toString());
        }

        int offset = (page - 1) * pageSize;
        map.put("userId", userId);
        map.put("offset", offset);
        map.put("pageSize", pageSize);

        List<NewsFavorite> favorites = newsFavoriteMapper.selectByUserIdWithPage(map);

        return ResultTool.success(favorites);
    }

    @Override
    public Result getUserFavoriteCount(Map<String, Object> map) {
        Integer userId = getUserIdFromToken(map);
        if (userId == null) {
            return ResultTool.success(0);
        }

        int count = newsFavoriteMapper.countByUserId(userId);
        return ResultTool.success(count);
    }

    @Override
    public Result getNewsFavoriteCount(Map<String, Object> map) {
        Integer newsId = Integer.parseInt(map.get("newsId").toString());
        int count = newsFavoriteMapper.countByNewsId(newsId);
        return ResultTool.success(count);
    }

    /**
     * 从token中获取用户ID
     */
    private Integer getUserIdFromToken(Map<String, Object> map) {
        try {
            String telephone = null;
            if (map.get("telephone") != null) {
                telephone = map.get("telephone").toString();
            } else if (map.get("token") != null) {
                // 从token中解析手机号
                String token = map.get("token").toString();
                // 这里简化处理，实际项目中应该从JWT token中解析
                // 暂时通过前端传递telephone
                return null;
            }

            if (telephone != null) {
                Map<String, Object> phoneMap = new HashMap<>();
                phoneMap.put("telephone", telephone);
                UserInf user = userMapper.selectPeopleByPhone(phoneMap);
                if (user != null) {
                    return user.getId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
