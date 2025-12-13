package com.example.community_education.Service.impl;

import com.example.community_education.Dao.CommodityMapper;
import com.example.community_education.Service.CommodityService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CommodityImpl implements CommodityService {
    @Resource
    CommodityMapper commodityMapper;
    @Override
    public Result GetGoods(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(commodityMapper.SelectGoods(map));
    }

    @Override
    public Result GoodsNumber(Map<String, Object> map) {
        return ResultTool.success(commodityMapper.GoodsNumber(map));
    }

    @Override
    public Result AnGoods(Map<String, Object> map) {
        System.out.println(map.get("id"));
        return ResultTool.success(commodityMapper.SelectAnGoods(map));
    }

    @Override
    public void DeleteCommodity(Map<String, Object> map) {
        commodityMapper.DeleteCommodity(map);
    }

    @Override
    public void updateImages(Map<String, Object> map) {
        commodityMapper.updateImages(map);
    }

    @Override
    public void ChangeCommodity(Map<String, Object> map) {
        commodityMapper.ChangeCommodity(map);
    }

    @Override
    public void AddCommodity(Map<String, Object> map) {
        commodityMapper.AddCommodity(map);
    }

    @Override
    public Result selectCommodity(Map<String, Object> map) {
        String name = (String)map.get("name");
        if(name != null && name != ""){
            map.put("name","%"+name+"%");
        }

        return ResultTool.success(commodityMapper.selectCommodity(map));
    }
}
