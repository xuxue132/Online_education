package com.example.community_education.Service.impl;

import com.example.community_education.Dao.FileMapper;
import com.example.community_education.Service.FileService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class FileImpl implements FileService {
    @Resource
    FileMapper fileMapper;

    @Override
    public Result TheFile(Map<String, Object> map) {
        return ResultTool.success(fileMapper.selectFile(map));
    }

    @Override
    public Result SumFile(Map<String, Object> map) {
        return ResultTool.success(fileMapper.SumFile(map));
    }

    @Override
    public Result AllFile(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(fileMapper.AllFile(map));
    }

    @Override
    public void AddFile(Map<String, Object> map) {
        fileMapper.AddFile(map);
    }

    @Override
    public void DeleteFileName(Map<String, Object> map) {
        fileMapper.DeleteFileName(map);
    }

    @Override
    public void DeleteFileNamePlus(Map<String, Object> map) {
        fileMapper.DeleteFileNamePlus(map);
    }
}
