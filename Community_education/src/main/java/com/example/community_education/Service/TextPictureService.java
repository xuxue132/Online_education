package com.example.community_education.Service;

import com.example.community_education.Tool.Result;
import com.qiniu.common.QiniuException;

import java.util.Map;

public interface TextPictureService {
    Result TextPicture(Map<String, Object> map);

    Result headURl(Map<String, Object> map);

    Result DeletePicture(Map<String, Object> map) throws QiniuException;
}
