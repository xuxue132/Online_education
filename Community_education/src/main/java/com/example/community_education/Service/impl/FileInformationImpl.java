package com.example.community_education.Service.impl;

import com.example.community_education.Dao.FileinformationMapper;
import com.example.community_education.Service.FileInformationService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class FileInformationImpl implements FileInformationService {
    @Resource
    FileinformationMapper fileinformationMapper;

    @Override
    public Result AllFileInformation(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(fileinformationMapper.selectFileInformation(map));
    }

    @Override
    public Result AnFileInformation(Map<String, Object> map) {
        return ResultTool.success(fileinformationMapper.selectAnFileInformation(map));
    }

    @Override
    public Result SumFileInformation(Map<String, Object> map) {
        return ResultTool.success(fileinformationMapper.SumFileInformation(map));
    }

    @Override
    public Result selectFileInformation(Map<String, Object> map) {
        String title = (String)map.get("title");
        if(title != null && title != ""){
            map.put("title","%"+title+"%");
        }
        return ResultTool.success(fileinformationMapper.selectFileInformations(map));
    }

    @Override
    public void AddFileInformations(Map<String, Object> map) {
        fileinformationMapper.AddFileInformations(map);
    }

    @Override
    public void UpdateFileInformations(Map<String, Object> map) {
        fileinformationMapper.UpdateFileInformations(map);
    }

    @Override
    public void DeleteFileName(Map<String, Object> map) {
        fileinformationMapper.DeleteFileName(map);
    }
}
