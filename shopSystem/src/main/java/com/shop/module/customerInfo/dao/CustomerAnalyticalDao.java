package com.shop.module.customerInfo.dao;

import com.alibaba.fastjson.JSONObject;
import com.shop.module.customerInfo.entity.AnalyticalDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/9/002 9:15
 */
@Mapper
public interface CustomerAnalyticalDao {

    List<JSONObject > selectCustomerAnalyticalData(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
    List<JSONObject> selectOrderAnalyticalData(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 查询用户及订单每日的数量
     * @param beginTime
     * @param endTime
     * @return
     */
    List<JSONObject> selectCustomerOrder(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
