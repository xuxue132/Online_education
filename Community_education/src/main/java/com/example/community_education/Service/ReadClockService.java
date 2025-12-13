package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface ReadClockService {
    Result Clock(Map<String, Object> map);

    Result ClockSelect(Map<String, Object> map);
}
