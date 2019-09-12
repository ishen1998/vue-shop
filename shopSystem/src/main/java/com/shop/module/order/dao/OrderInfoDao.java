package com.shop.module.order.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.order.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * @author 28166
 */
@Mapper
public interface OrderInfoDao {

    // 根据订单ID查询订单明细信息标题
    OrderInfo queryOrderDetailTitle(String orderId);

    // 根据条件查询订单
    List<OrderInfo> queryOrder(Page page, String orderNo, String startTime, String endTime, String payStatus);

    // 查询未支付超时订单
    List<OrderInfo> queryOverTimeOrder(Integer minute);

    // 分页查询所有订单
    List<OrderInfo> queryOrderAll(Page page);

    // 查询已支付未确认订单
    List<OrderInfo> queryUnconfirmed(Integer day);

    // 查询所有订单
    List<OrderInfo> queryAll();

    // 根据订单状态和支付状态查询订单
    List<OrderInfo> queryByState(String cId, String orderStatus, String payStatus, String receiptStatus);

    // 根据客户ID查询订单
    List<OrderInfo> queryOrderBycId(String cId);

    // 批量删除订单
    int deleteByPrimaryKeys(@Param("list") List<String> orderIds);

    // 修改多个订单状态
    int updateOrderStatus(List<String> list, String orderStatus, String receiptStatus);

    // 订单分析
    List<JSONObject> queryOrderData(@Param("beginTime") String beginTime,@Param("orderStatus") String orderStatus);

    int updateStatusByOrderIds(@Param("list") List<String> orderIds, String payStatus, String receiptStatus);

    int deleteByPrimaryKey(String orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    int selectByItemId(String itemId);

    OrderInfo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}