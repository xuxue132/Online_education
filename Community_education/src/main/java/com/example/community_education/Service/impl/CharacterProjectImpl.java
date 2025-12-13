package com.example.community_education.Service.impl;

import com.example.community_education.Dao.CharacterProjectMapper;
import com.example.community_education.Service.CharacterProjectService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
@Service
public class CharacterProjectImpl implements CharacterProjectService {
    @Resource
    CharacterProjectMapper characterProjectMapper;
    @Override
    public Result selectAll(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(characterProjectMapper.AllProject(map));
    }

    @Override
    public Result Number() {
        return ResultTool.success(characterProjectMapper.ProjectNumber());
    }

    @Override
    public Result TheProject(Map<String, Object> map) {
        return ResultTool.success(characterProjectMapper.AnProject(map));
    }

    @Override
    public Result selectCharacterProject(Map<String, Object> map) {
        String title = (String)map.get("title");
        String area = (String)map.get("area");
        if(title != null && title != ""){
            map.put("title","%"+title+"%");
        }
        if(area != null && area != ""){
            map.put("area","%"+area+"%");
        }
        return ResultTool.success(characterProjectMapper.selectCharacterProject(map));
    }

    @Override
    public int AddCharacterProject(Map<String, Object> map) {
        return characterProjectMapper.insert(map);
    }

    @Override
    public void updateImages(Map<String, Object> map) {
        characterProjectMapper.updateImages(map);
    }

    @Override
    public void UpdateCharacterProject(Map<String, Object> map) {
        characterProjectMapper.update(map);
    }

    @Override
    public void DeleteCharacterProject(Map<String, Object> map) {
        characterProjectMapper.delete(map);
    }
}
