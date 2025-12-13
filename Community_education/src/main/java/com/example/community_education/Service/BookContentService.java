package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface BookContentService {
    Result OneBook(Map<String, Object> map);

    Result BookText(Map<String, Object> map);

    Result AllBookContentSum(Map<String, Object> map);

    Result AllBookContent(Map<String, Object> map);

    Result DeleteBookContent(Map<String, Object> map);

    Result AddBookContents(Map<String, Object> map);

    void DeleteBookContentPlus(Map<String, Object> map);

    Result ChangeBookContents(Map<String, Object> map);
}
