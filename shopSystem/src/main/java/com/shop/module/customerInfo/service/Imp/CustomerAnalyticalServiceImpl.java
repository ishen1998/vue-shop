package com.shop.module.customerInfo.service.Imp;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.shop.module.customerInfo.dao.CustomerAnalyticalDao;
import com.shop.module.customerInfo.entity.AnalyticalDate;
import com.shop.module.customerInfo.service.CustomerAnalyticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/9/002 8:58
 */
@Service
public class CustomerAnalyticalServiceImpl implements CustomerAnalyticalService {

    @Autowired
    private CustomerAnalyticalDao customerAnalyticalDao;

    @Override
    public Map<String, Object> analyticaldata(long beginTime,long endTime) {
        Map<String, Object> map = new HashMap<>();
        DateTime time = new DateTime(beginTime);
        String begin = time.toString("yyyy-MM-dd");
        time.setTime(endTime);
        String end = time.toString("yyyy-MM-dd");
        List<JSONObject > cu = customerAnalyticalDao.selectCustomerAnalyticalData(begin,end);
        List<JSONObject > order = customerAnalyticalDao.selectOrderAnalyticalData(begin, end);
        map.put("customer", cu);
        map.put("order", order);
        return map;
    }

    /**
     * 获取订单及用户每天的数量
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<JSONObject> getData(long beginTime,long endTime){
        DateTime time = new DateTime(beginTime);
        String begin = time.toString("yyyy-MM-dd");
        time.setTime(endTime);
        String end = time.toString("yyyy-MM-dd");
        List<JSONObject> jsonObjects = customerAnalyticalDao.selectCustomerOrder(begin, end);
        return jsonObjects;
    }
}
