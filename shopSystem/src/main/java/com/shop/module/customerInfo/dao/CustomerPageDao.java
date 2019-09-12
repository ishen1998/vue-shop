package com.shop.module.customerInfo.dao;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.entity.CustomerInfoTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerPageDao extends BaseMapper<CustomerInfo> {
    IPage<JSONObject> selectUserTicketList(Page page);
    CustomerInfoTicket selectByMobile(@Param("mobile") String mobile);
    Integer countUser();
}
