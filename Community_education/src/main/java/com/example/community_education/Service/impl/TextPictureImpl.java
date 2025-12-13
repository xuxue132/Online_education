package com.example.community_education.Service.impl;

import com.example.community_education.Controller.File.QiNiuSupport;
import com.example.community_education.Dao.TextPictureMapper;
import com.example.community_education.Service.TextPictureService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.qiniu.common.QiniuException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TextPictureImpl implements TextPictureService {
    @Resource
    TextPictureMapper textPictureMapper;
    @Resource
    QiNiuSupport qiNiuSupport;
    @Override
    public Result TextPicture(Map<String, Object> map) {
        return ResultTool.success(textPictureMapper.select(map));
    }

    @Override
    public Result headURl(Map<String, Object> map) {
        textPictureMapper.insert(map);
        return ResultTool.success("修改成功！");
    }

    @Override
    public Result DeletePicture(Map<String, Object> map) throws QiniuException {
        String name = map.get("outId")+"-"+map.get("name");
        qiNiuSupport.delete(name);
        textPictureMapper.deleteByPrimaryKey((Integer) map.get("id"));
        return ResultTool.success("删除成功！");
    }
}
