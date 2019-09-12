package com.shop.module.order.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.order.entity.OrderDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface OrderDetailInfoDao {


    // 根据订单ID查询商品信息
    List<OrderDetailInfo> queryOrderDetail(Page page, String orderId);

    List<OrderDetailInfo> queryOrderDetail(String orderId);

    // 查询所有商品信息
    List<OrderDetailInfo> queryOrderDetailAll();

    // 批量插入订单详情
    int insertOrderList(List<OrderDetailInfo> list);

    // 根据商品ID修改商品状态
    int updateByItemId(String itemId,@Param("itemStatus") String itemStatus);


    int deleteByPrimaryKey(String orderDetailId);

    int insert(OrderDetailInfo record);

    int insertSelective(OrderDetailInfo record);

    OrderDetailInfo selectByPrimaryKey(String orderDetailId);

    int updateByPrimaryKeySelective(OrderDetailInfo record);

    int updateByPrimaryKey(OrderDetailInfo record);
}