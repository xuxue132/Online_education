package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface OrderFormService {
    Result GoodsAdd(Map<String, Object> map);

    Result SelectAll(Map<String, Object> map);

    Result updateStates(Map<String, Object> map);

    Result OrderSum(Map<String, Object> map);

}
