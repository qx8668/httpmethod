package com.app.code.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public class FastJsonTool {

    //对单个json的解析
    public static <T> T get(String value, Class<T> cls) {
        T t = JSON.parseObject(value, cls);
        return t;
    }

    //对多个json的解析
    public static <T> List<T> getList(String value, Class<T> cls) {
        List<T> list = JSON.parseArray(value, cls);
        return list;
    }
}
