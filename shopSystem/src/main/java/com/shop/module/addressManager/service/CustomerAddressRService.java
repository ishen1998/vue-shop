package com.shop.module.addressManager.service;

import com.shop.module.addressManager.entity.CustomerAddressR;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import java.util.List;


public interface CustomerAddressRService {

    int deleteByPrimaryKey(String id) throws CustomizeExp;

    int insert(CustomerAddressR address) throws CustomizeExp;

    CustomerAddressR selectByPrimaryKey(String id) throws CustomizeExp;

    int updateByPrimaryKey(CustomerAddressR address) throws CustomizeExp;

    List<CustomerAddressR> selectByCustomerId(String cId) throws CustomizeExp;

    int updateStatusByAddId(String customerAddressId, String addressStatus);
}