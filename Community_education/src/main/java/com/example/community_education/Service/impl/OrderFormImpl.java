package com.example.community_education.Service.impl;

import com.example.community_education.Dao.OrderFormMapper;
import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Service.OrderFormService;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class OrderFormImpl implements OrderFormService {
    @Resource
    OrderFormMapper orderFormMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public Result GoodsAdd(Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        int i = (int) map.get("points");
        map.put("sorce",i-2*i);
        userMapper.ChangeIntegral(map);
        return ResultTool.success(orderFormMapper.insert(map));
    }

    @Override
    public Result SelectAll(Map<String, Object> map) {
        map.put("NowPage",(Integer.parseInt(""+ map.get("NowPage"))-1)*10);
        return ResultTool.success(orderFormMapper.SelectAll(map));
    }

    @Override
    public Result updateStates(Map<String, Object> map) {
        orderFormMapper.updateStates(map);
        return ResultTool.success("修改成功");
    }

    @Override
    public Result OrderSum(Map<String, Object> map) {
        return ResultTool.success(orderFormMapper.OrderSum(map));
    }

    public Result MyGoods(Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        return ResultTool.success(orderFormMapper.SelectMyGoods(map));
    }
}
