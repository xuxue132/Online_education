package com.example.community_education.Dao;

import com.example.community_education.Model.OrderForm;

import java.util.List;
import java.util.Map;

import com.example.community_education.Model.OrderFormPlus;
import org.apache.ibatis.annotations.Param;

public interface OrderFormMapper {

    int insert(Map<String, Object> map);

    List<OrderFormPlus> SelectMyGoods(Map<String, Object> map);

    List<OrderForm> SelectAll(Map<String, Object> map);

    void updateStates(Map<String, Object> map);

    int OrderSum(Map<String, Object> map);

}