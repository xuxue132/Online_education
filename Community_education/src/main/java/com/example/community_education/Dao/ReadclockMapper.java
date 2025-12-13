package com.example.community_education.Dao;

import com.example.community_education.Model.Readclock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ReadclockMapper {

    int ReadClock(Map<String, Object> map);

    List<Readclock> UserClock(Map<String, Object> map);
}