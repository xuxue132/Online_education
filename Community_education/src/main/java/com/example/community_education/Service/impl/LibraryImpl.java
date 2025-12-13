package com.example.community_education.Service.impl;

import com.example.community_education.Dao.LibraryMapper;
import com.example.community_education.Service.LibraryService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class LibraryImpl implements LibraryService {
    @Resource
    LibraryMapper libraryMapper;

    @Override
    public Result Books(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(libraryMapper.selectBooks(map));
    }

    @Override
    public Result ToTalData(Map<String, Object> map) {
        return  ResultTool.success(libraryMapper.BookNumber(map));
    }

    @Override
    public Result TheBook(Map<String, Object> map) {
        return ResultTool.success(libraryMapper.AnBook(map));
    }

    @Override
    public Result selectLibrary(Map<String, Object> map) {
        String name_book = (String)map.get("name_book");
        if(name_book != null && name_book != ""){
            map.put("name_book","%"+name_book+"%");
        }
        return ResultTool.success(libraryMapper.selectLibrary(map));
    }

    @Override
    public Result AddLibrary(Map<String, Object> map) {
        return ResultTool.success(libraryMapper.AddLibrary(map));
    }

    @Override
    public Result UpdateLibrary(Map<String, Object> map) {
        return ResultTool.success(libraryMapper.UpdateLibrary(map));
    }

    @Override
    public Result UpdateLibraryPicture(Map<String, Object> map) {
        return ResultTool.success(libraryMapper.UpdateLibraryPicture(map));
    }

    @Override
    public Result DeleteLibrary(Map<String, Object> map) {
        return ResultTool.success(libraryMapper.DeleteLibrary(map));
    }
}
