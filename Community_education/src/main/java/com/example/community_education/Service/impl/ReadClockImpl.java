package com.example.community_education.Service.impl;

import com.example.community_education.Dao.ReadclockMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Service.ReadClockService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ReadClockImpl implements ReadClockService {
    @Resource
    ReadclockMapper readclockMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public Result Clock(Map<String, Object> map) {
        userMapper.ChangeIntegral(map);
        return ResultTool.success(readclockMapper.ReadClock(map));
    }

    @Override
    public Result ClockSelect(Map<String, Object> map) {
        return ResultTool.success(readclockMapper.UserClock(map));
    }
}
