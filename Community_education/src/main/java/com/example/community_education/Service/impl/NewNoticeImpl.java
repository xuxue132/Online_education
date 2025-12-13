package com.example.community_education.Service.impl;

import com.example.community_education.Dao.NewNoticeMapper;
import com.example.community_education.Service.NewNotice;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class NewNoticeImpl implements NewNotice {
    @Resource
    NewNoticeMapper newNoticeMapper;
    @Override
    public Result AllData(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(newNoticeMapper.AllNewNotice(map));
    }

    @Override
    public Result ToTalData(Map<String, Object> map) {
        return ResultTool.success(newNoticeMapper.NewNoticeNumber(map));
    }

    @Override
    public Result AllDatas(Map<String, Object> map) {
         map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(newNoticeMapper.AllNewNotices(map));
    }

    @Override
    public Result selectNewNotice(Map<String, Object> map) {
        String title = (String)map.get("title");
        String author = (String)map.get("author");
        if(title != null && title != ""){
            map.put("title","%"+title+"%");
        }
        if(author != null && author != ""){
            map.put("author","%"+author+"%");
        }
        return ResultTool.success(newNoticeMapper.selectNewNotice(map));
    }

    @Override
    public Result DeleteNewNotice(Map<String, Object> map) {
        newNoticeMapper.deleteNewNotice(map);
        return ResultTool.success("删除成功");
    }

    @Override
    public Result Add(Map<String, Object> map) {
        newNoticeMapper.insert(map);
        return ResultTool.success("添加成功");
    }

    @Override
    public Result UpdateNewNotice(Map<String, Object> map) {
        newNoticeMapper.updateByPrimaryKey(map);
        return ResultTool.success("修改成功");
    }

    @Override
    public void NewNoticeHit(Map<String, Object> map) {
        newNoticeMapper.NewNoticeHit(map);
    }

    public Result TheData(Map<String, Object> map) {
        return ResultTool.success(newNoticeMapper.TheNewNotice(map));
    }
}
