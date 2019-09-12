package com.shop.module.customerInfo.dao;


import com.shop.datasource.annotation.DataSource;
import com.shop.module.customerInfo.entity.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CustomerInfoDao继承基类
 */
@Mapper
public interface CustomerInfoDao {

    /**
     * 通过客户id删除信息
     */
    int deleteByPrimaryKey(String id);

    /**
     * 添加客户信息
     */
    int insert(CustomerInfo customer);

    /**
     * 通过id查询客户信息
     */
    CustomerInfo selectByPrimaryKey(String id);

    /**
     * 通过客户id更新客户信息
     */
    int updateByPrimaryKey(CustomerInfo customer);

    /**
     * 通过客户手机号查询客户信息
     */
    CustomerInfo selectByMobileOrName(@Param("mobile") String mobile, @Param("cLoginName") String cLoginName);

    /**
     * 查询所有客户信息
     */
    List<CustomerInfo> selectAll();

    /**
     * 通过客户名信息
     */
    CustomerInfo selectByLoginName(String cLoginName);

    int updatePwdByCname(@Param("cLoginName") String cLoginName, @Param("password") String password);

    int updateCustomerStatus(@Param("cId") String cId, @Param("status") String status);
}