package com.shop.module.order.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.order.dao.OrderDetailInfoDao;
import com.shop.module.order.entity.OrderDetailInfo;
import com.shop.module.order.service.OrderDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 28166
 */
@Service
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {



    @Autowired
    private OrderDetailInfoDao orderDetailInfoDao;

    @Override
    public List<OrderDetailInfo> queryOrderDetail(Page page, String orderId) {
        return orderDetailInfoDao.queryOrderDetail(page,orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrderList(List<OrderDetailInfo> list) {
        return orderDetailInfoDao.insertOrderList(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByItemId(String itemId, String itemStatus) {
        return orderDetailInfoDao.updateByItemId(itemId,itemStatus);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String orderDetailId) {
        return orderDetailInfoDao.deleteByPrimaryKey(orderDetailId);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(OrderDetailInfo record) {
        return orderDetailInfoDao.insert(record);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertSelective(OrderDetailInfo record) {
        return orderDetailInfoDao.insertSelective(record);
    }



    @Override
    public OrderDetailInfo selectByPrimaryKey(String orderDetailId) {
        return orderDetailInfoDao.selectByPrimaryKey(orderDetailId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(OrderDetailInfo record) {
        return orderDetailInfoDao.updateByPrimaryKeySelective(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(OrderDetailInfo record) {
        return orderDetailInfoDao.updateByPrimaryKey(record);
    }
}
