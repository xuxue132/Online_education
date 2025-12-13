package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface NewNotice {
    Result AllData(Map<String, Object> map);

    Result ToTalData(Map<String, Object> map);

    Result AllDatas(Map<String, Object> map);

    Result selectNewNotice(Map<String, Object> map);

    Result DeleteNewNotice(Map<String, Object> map);

    Result Add(Map<String, Object> map);

    Result UpdateNewNotice(Map<String, Object> map);

    void NewNoticeHit(Map<String, Object> map);
}
