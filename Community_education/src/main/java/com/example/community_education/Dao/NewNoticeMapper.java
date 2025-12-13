package com.example.community_education.Dao;

import com.example.community_education.Model.NewNotice;

import java.util.List;
import java.util.Map;

import com.example.community_education.Tool.Result;
import org.apache.ibatis.annotations.Param;

public interface NewNoticeMapper {


    int deleteByPrimaryKey(Integer id);

    int insertSelective(NewNotice record);


    NewNotice selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(NewNotice record);




    List<NewNotice> AllNewNotice(Map<String, Object> map);
    List<NewNotice> AllNewNotices(Map<String, Object> map);

    NewNotice TheNewNotice(Map<String, Object> map);

    int NewNoticeNumber(Map<String, Object> map);

    List<NewNotice> selectNewNotice(Map<String, Object> map);

    void deleteNewNotice(Map<String, Object> map);

    int insert(Map<String, Object> map);

    int updateByPrimaryKey(Map<String, Object> map);

    void NewNoticeHit(Map<String, Object> map);
}