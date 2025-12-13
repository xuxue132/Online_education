package com.example.community_education.Service.impl;

import com.example.community_education.Dao.BookContentMapper;
import com.example.community_education.Service.BookContentService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class BookContentImpl implements BookContentService {
    @Resource
    BookContentMapper bookContentMapper;
    @Override
    public Result OneBook(Map<String, Object> map) {
        return ResultTool.success(bookContentMapper.TheBook(map));
    }

    @Override
    public Result BookText(Map<String, Object> map) {
        return ResultTool.success(bookContentMapper.TheBookText(map));
    }

    @Override
    public Result AllBookContentSum(Map<String, Object> map) {
        return ResultTool.success(bookContentMapper.AllBookContentSum(map));
    }

    @Override
    public Result AllBookContent(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(bookContentMapper.AllBookContent(map));
    }

    @Override
    public Result DeleteBookContent(Map<String, Object> map) {
        return ResultTool.success(bookContentMapper.DeleteBookContent(map));
    }

    @Override
    public Result AddBookContents(Map<String, Object> map) {
        return ResultTool.success(bookContentMapper.AddBookContents(map));
    }

    @Override
    public void DeleteBookContentPlus(Map<String, Object> map) {
        bookContentMapper.DeleteBookContentPlus(map);
    }

    @Override
    public Result ChangeBookContents(Map<String, Object> map) {
        return ResultTool.success(bookContentMapper.ChangeBookContents(map));
    }
}
