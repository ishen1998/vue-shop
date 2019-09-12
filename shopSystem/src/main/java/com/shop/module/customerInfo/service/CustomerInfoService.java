package com.shop.module.customerInfo.service;


import com.shop.common.CommonResult;
import com.shop.common.Result;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.entity.CustomerInfoTicket;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerInfoService{

    int deleteByPrimaryKey(String id) throws CustomizeExp;

    int insert(CustomerInfo customer) throws CustomizeExp;

    CustomerInfo selectByPrimaryKey(String id) throws CustomizeExp;

    CustomerInfo updateByPrimaryKey(CustomerInfo customer) throws CustomizeExp;

   CustomerInfo selectByMobileOrName(@Param("mobile") String mobile,@Param("cLoginName") String cLoginName);

   CustomerInfo selectByLoginName(String cLoginName);

    CustomerInfoTicket selectByMobile(String mobile) throws CustomizeExp;

    Map pageHolder(Integer current, Integer size) throws CustomizeExp;
    /**
     * 登入成功获取后获取Token
     * @param customerInfo
     * @return
     */
   Map loginToken(CustomerInfo customerInfo) throws CustomizeExp;

    Map checkToken(String token) throws CustomizeExp;

    int enableCustomers(String cId, String status) throws CustomizeExp;
}
