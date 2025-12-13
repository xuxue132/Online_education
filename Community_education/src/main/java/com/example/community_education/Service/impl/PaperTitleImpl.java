package com.example.community_education.Service.impl;

import com.example.community_education.Dao.PaperTitleMapper;
import com.example.community_education.Service.PaperTitleService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
@Service
public class PaperTitleImpl implements PaperTitleService {
    @Resource
    PaperTitleMapper paperTitleMapper;
    @Override
    public Result Paper(Map<String, Object> map) {
        return ResultTool.success(paperTitleMapper.selectTitle(map));
    }

    @Override
    public Result AllTitle(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(paperTitleMapper.AllTitle(map));
    }

    @Override
    public Result AllTitleSum(Map<String, Object> map) {
        return ResultTool.success(paperTitleMapper.AllTitleSum(map));
    }

    @Override
    public void Delete(Map<String, Object> map) {
        paperTitleMapper.Delete(map);
    }

    @Override
    public void DeleteTitle(Map<String, Object> map) {
        paperTitleMapper.DeleteTitle(map);
    }

    @Override
    public Result UpdateTitle(Map<String, Object> map) {
        paperTitleMapper.updateByPrimaryKey(map);
        return ResultTool.success("修改成功");
    }

    @Override
    public void AddTitle(Map<String, Object> map) {
        paperTitleMapper.insert(map);
    }

    @Override
    public Result selectTitle(Map<String, Object> map) {
        return ResultTool.success(paperTitleMapper.selectTitleS(map));
    }
}
