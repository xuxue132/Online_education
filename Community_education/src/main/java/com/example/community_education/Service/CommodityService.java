package com.example.community_education.Service;

import com.example.community_education.Tool.Result;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CommodityService {
    Result GetGoods(Map<String, Object> map);

    Result GoodsNumber(Map<String, Object> map);

    Result AnGoods(Map<String, Object> map);

    void DeleteCommodity(Map<String, Object> map);

    void updateImages(Map<String, Object> map);

    void ChangeCommodity(Map<String, Object> map);

    void AddCommodity(Map<String, Object> map);

    Result selectCommodity(Map<String, Object> map);
}
