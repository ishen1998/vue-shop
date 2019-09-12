package com.shop.module.ticketManager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.ticketManager.entity.TicketCustomerR;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TicketCustomerRDAO继承基类
 */
@Mapper
public interface TicketCustomerRDAO{
    int deleteCustomerTicket(String id);

    int insert(TicketCustomerR ticketCustomerR);

    TicketCustomerR selectByCustomerTicketId(@Param("cId") String cId,@Param("ticketId") String ticketId);

    List<TicketCustomerR> selectAllCustomerTic();

    int updateTicCusStatus(@Param("ticketCustomerId") String ticketCustomerId,@Param("useStatus") String useStatus);

    TicketCustomerR selectCusTicketById(String id);

    IPage<TicketCustomerR> customerTicketPage(Page page,@Param("mobile") String mobile);

    Integer selectCountTicket(@Param("mobile") String mobile);
}