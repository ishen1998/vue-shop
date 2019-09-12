package com.shop.module.order.service;


import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.entity.OrderDetailInfo;
import com.shop.module.order.entity.OrderInfo;
import com.shop.module.order.entity.OrderParam;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 28166
 */
public interface OrderService {

    // 订单分析
    Map<String, Object> orderAnalyzing(Long beginTime);

    // 生成订单
    Map<String, Object> generateOrder(OrderParam record) throws CustomizeExp;

    // 订单支付
    int paySuccess(String orderId) throws CustomizeExp;

    // 合并支付
    int payTogether(List<String> orderIds) throws CustomizeExp;

    // 取消订单
    int cancelOrder(String orderId) throws CustomizeExp;

    // 确认订单
    int confirmOrder(String orderId) throws CustomizeExp;

    // 退款/退货
    int recedeOrder(String orderId) throws CustomizeExp;

    // 自动取消未支付超时订单
    void cancelTimeOutOrder() throws CustomizeExp;

    // 自动确认已支付订单
    void confirmedOrder();
}
