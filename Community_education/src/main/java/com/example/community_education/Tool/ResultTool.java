package com.example.community_education.Tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后端返回给前端的统一设置数据工具
 */
public class ResultTool {

    /**
     *  当请求成功并且后端返回给前端的是一个列表json时使用
     */
    @SuppressWarnings("unchecked")
    public static Result success(List<Object> object,String message){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        result.setMessage(message);
        return result;
    }

    /**
     *  当请求成功并且返回给前端是一个对象时使用
     */
    @SuppressWarnings("unchecked")
    public static Result success(Object object,String message){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        result.setMessage(message);
        return result;
    }
    /**
     *  当请求成功并且返回给前端是一个对象时使用
     */
    @SuppressWarnings("unchecked")
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        return result;
    }

    /**
     *  当请求成功并且返回给前端是一个int对象时使用
     */
    @SuppressWarnings("unchecked")
    public static Result success(int object){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        return result;
    }

    /**
     *  当请求成功并且后端返回给前端的是一个列表json时使用
     */
    @SuppressWarnings("unchecked")
    public static Result success(List<Object> object){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        return result;
    }
    /**
     * 当请求成功并且不需要返回任何数据时使用
     */
    public static Result success(String message){
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }
    /**
     * 当请求成功并且不需要返回任何数据时使用
     */
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        return result;
    }

    /**
     * 当请求失败并不需要返回任何信息时使用
     */
    public static Result error(){
        Result result = new Result();
        result.setCode(400);
        return result;
    }

    /**
     * 当请求失败并且返回失败信息时使用
     */
    public static Result error(String message){
        Result result = new Result();
        result.setMessage(message);
        result.setCode(400);
        return result;
    }

    public static Result successMap(List list,String count){
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("info",list);
        map1.put("count",count);
        return ResultTool.success(map1);

    }

//    public static Result successMap(List list,String count,String message){
//        Map<String,Object> map1 = new HashMap<String,Object>();
//        map1.put("info",list);
//        map1.put("count",count);
//        return ResultTool.success(map1);
//    }
}
