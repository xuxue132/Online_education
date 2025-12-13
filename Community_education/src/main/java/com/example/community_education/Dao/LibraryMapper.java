package com.example.community_education.Dao;

import com.example.community_education.Model.Library;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LibraryMapper {


    int insert(Library record);

    int insertSelective(Library record);


    List<Library> selectBooks(Map<String, Object> map);

    int BookNumber(Map<String, Object> map);

    Library AnBook(Map<String, Object> map);

    List<Library> selectLibrary(Map<String, Object> map);

    int AddLibrary(Map<String, Object> map);

    int UpdateLibrary(Map<String, Object> map);

    int UpdateLibraryPicture(Map<String, Object> map);

    int DeleteLibrary(Map<String, Object> map);
}