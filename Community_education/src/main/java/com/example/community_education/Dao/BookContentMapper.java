package com.example.community_education.Dao;

import com.example.community_education.Model.BookContent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BookContentMapper {


    List<BookContent> TheBook(Map<String, Object> map);

    BookContent TheBookText(Map<String, Object> map);

    int AllBookContentSum(Map<String, Object> map);

    List<BookContent> AllBookContent(Map<String, Object> map);

    int DeleteBookContent(Map<String, Object> map);

    int AddBookContents(Map<String, Object> map);

    void DeleteBookContentPlus(Map<String, Object> map);

    int ChangeBookContents(Map<String, Object> map);
}