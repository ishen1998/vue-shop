package com.shop.module.customerInfo.service;

import com.alibaba.fastjson.JSONObject;
import com.shop.module.customerInfo.entity.AnalyticalDate;

import java.util.List;
import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/9/002 8:58
 */
public interface CustomerAnalyticalService {
    Map<String, Object> analyticaldata(long beginTime,long endTime);

    List<JSONObject> getData(long beginTime, long endTime);
}
