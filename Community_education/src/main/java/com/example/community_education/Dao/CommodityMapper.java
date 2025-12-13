package com.example.community_education.Dao;

import com.example.community_education.Model.Commodity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommodityMapper {




    List<Commodity> SelectGoods(Map<String, Object> map);

    Commodity SelectAnGoods(Map<String, Object> map);

    int GoodsNumber(Map<String, Object> map);

    void DeleteCommodity(Map<String, Object> map);

    void updateImages(Map<String, Object> map);

    void ChangeCommodity(Map<String, Object> map);

    void AddCommodity(Map<String, Object> map);

    List<Commodity> selectCommodity(Map<String, Object> map);
}