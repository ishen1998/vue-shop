package com.shop.module.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.entity.OrderInfo;

import java.util.List;

public interface OrderInfoService {

    // 根据订单ID查询订单明细信息标题
    OrderInfo queryOrderDetailTitle(String orderId);

    // 根据条件查询订单
    List<OrderInfo> queryOrder(Page page, String orderNo, String startTime, String endTime, String payStatus);

    // 分页查询所有订单
    List<OrderInfo> queryOrderAll(Page page);

    // 查询所有订单
    List<OrderInfo> queryAll();

    // 根据订单状态和支付状态查询订单
    List<OrderInfo> queryByState(String cId, String orderStatus, String payStatus, String receiptStatus);

    // 根据客户ID查询订单
    List<OrderInfo> queryOrderBycId(String cId);

    // 批量删除订单
    int deleteByPrimaryKeys(List<String> orderIds) throws CustomizeExp;

    int deleteByPrimaryKey(String orderId) throws CustomizeExp;

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record) throws CustomizeExp;

    // 根据商品ID查询该商品进行中的订单数量
    int selectByItemId(String itemId);

    OrderInfo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);


}
