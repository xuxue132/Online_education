package com.example.community_education.Service.impl;

import com.example.community_education.Dao.HonorMapper;
import com.example.community_education.Service.HonorService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class HonorImpl implements HonorService {
    @Resource
    HonorMapper honorMapper;
    @Override
    public Result AllHonorSum(Map<String, Object> map) {
        return ResultTool.success(honorMapper.AllHonorSum(map));
    }

    @Override
    public Result AllHonor(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(honorMapper.SelectHonor(map));
    }

    @Override
    public Result AnHonor(Map<String, Object> map) {
        return ResultTool.success(honorMapper.SelectAnHonor(map));
    }

    @Override
    public void DeleteHonor(Map<String, Object> map) {
        honorMapper.DeleteHonor(map);
    }

    @Override
    public void HonorUpload(Map<String, Object> map) {
        honorMapper.HonorUpload(map);
    }

    @Override
    public void ChangeHonor(Map<String, Object> map) {
        honorMapper.ChangeHonor(map);
    }

    @Override
    public void AddHonor(Map<String, Object> map) {
        honorMapper.Add(map);
    }

    @Override
    public Result selectHonor(Map<String, Object> map) {
        String name = (String)map.get("title");
        if(name != null && name != ""){
            map.put("title","%"+name+"%");
        }
        return ResultTool.success(honorMapper.selectHonor(map));
    }
}
