package com.shop.module.order.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.dao.OrderInfoDao;
import com.shop.module.order.entity.OrderInfo;
import com.shop.module.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 28166
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {


    @Autowired
    private OrderInfoDao orderInfoDao;

    @Override
    public OrderInfo queryOrderDetailTitle(String orderId){
        return orderInfoDao.queryOrderDetailTitle(orderId);
    }

    @Override
    public List<OrderInfo> queryOrder(Page page, String orderNo, String startTime, String endTime, String payStatus) {
        return orderInfoDao.queryOrder(page,orderNo,startTime,endTime,payStatus);
    }

    @Override
    public List<OrderInfo> queryOrderAll(Page page) {
        return orderInfoDao.queryOrderAll(page);
    }

    @Override
    public List<OrderInfo> queryAll() {
        return orderInfoDao.queryAll();
    }

    @Override
    public List<OrderInfo> queryByState(String cId, String orderStatus, String payStatus, String receiptStatus) {
        return orderInfoDao.queryByState(cId,orderStatus,payStatus,receiptStatus);
    }

    @Override
    public List<OrderInfo> queryOrderBycId(String cId) {
        return orderInfoDao.queryOrderBycId(cId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKeys(List<String> orderIds) throws CustomizeExp {
        int order = orderInfoDao.deleteByPrimaryKeys(orderIds);
        if (order==0){
            throw new CustomizeExp("删除异常");
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String orderId) throws CustomizeExp {
        int order = orderInfoDao.deleteByPrimaryKey(orderId);
        if (order==0){
            throw new CustomizeExp("删除异常");
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(OrderInfo record) {
        return orderInfoDao.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertSelective(OrderInfo record) throws CustomizeExp {
        int order = orderInfoDao.insertSelective(record);
        if(order==0){
            throw new CustomizeExp("添加异常");
        }
        return order;
    }

    @Override
    public int selectByItemId(String itemId) {
        return orderInfoDao.selectByItemId(itemId);
    }

    @Override
    public OrderInfo selectByPrimaryKey(String orderId) {
        return orderInfoDao.selectByPrimaryKey(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(OrderInfo record) {
        int order = orderInfoDao.updateByPrimaryKeySelective(record);
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(OrderInfo record) {
        return orderInfoDao.updateByPrimaryKey(record);
    }
}
