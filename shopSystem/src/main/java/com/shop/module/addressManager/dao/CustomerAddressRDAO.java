package com.shop.module.addressManager.dao;

import com.shop.module.addressManager.entity.CustomerAddressR;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CustomerAddressRDAO继承基类
 */
@Mapper
public interface CustomerAddressRDAO{
    int deleteByPrimaryKey(String id);

    int insert(CustomerAddressR address);

    CustomerAddressR selectByPrimaryKey(String id);

    int updateByPrimaryKey(CustomerAddressR address);

    List<CustomerAddressR> selectByCustomerId(@Param("cId") String cId);

    int updateStatusByAddId(@Param("customerAddressId") String customerAddressId,@Param("addressStatus") String addressStatus);
}