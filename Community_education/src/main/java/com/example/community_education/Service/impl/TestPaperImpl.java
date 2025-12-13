package com.example.community_education.Service.impl;

import com.example.community_education.Dao.TestPaperMapper;
import com.example.community_education.Service.TestPaperService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
@Service
public class TestPaperImpl implements TestPaperService {
    @Resource
    TestPaperMapper testPaperMapper;

    @Override
    public Result Papers(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(testPaperMapper.AllPaper(map));
    }

    @Override
    public Result Number() {
        return ResultTool.success(testPaperMapper.PaperSum());
    }

    @Override
    public void Delete(Map<String, Object> map) {
        testPaperMapper.Delete(map);
    }

    @Override
    public void UpdatePaper(Map<String, Object> map) {
        testPaperMapper.updateByPrimaryKey(map);
    }

    @Override
    public void AddPaper(Map<String, Object> map) {
        map.put("dates", new Timestamp(new Date().getTime()));
        testPaperMapper.AddPaper(map);
    }

    @Override
    public void ChangeEffect(Map<String, Object> map) {
        testPaperMapper.ChangeEffect(map);
    }

    @Override
    public Result selectPaper(Map<String, Object> map) {
        String title = (String)map.get("title");
        if(title != null && title != ""){
            map.put("title","%"+title+"%");
        }
        return ResultTool.success(testPaperMapper.selectPaper(map));
    }

}
