package com.shop.module.ticketManager.dao;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.ticketManager.entity.TicketInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TicketInfoDAO继承基类
 */
@Mapper
public interface TicketInfoDAO{
    int deleteByPrimaryKey(@Param("ticketId") String id);

    int insert(TicketInfo ticketInfo);

    List<TicketInfo> selectAllTicket( );

    TicketInfo selectByPrimaryKey(String id);

    int updateByPrimaryKey(TicketInfo ticketInfo);

    int updateOnlyTicketStatus(@Param("ticketId") String ticketId, @Param("ticketStatus") String ticketStatus);

    IPage<TicketInfo> ticketPageHelper(Page page);

    Integer countTicket( );

    IPage<TicketInfo> selectTicketCreateBy(Page page,@Param("createdBy") String createdBy,@Param("ticketStatus") String ticketStatus);
    Integer selectTicketCreateByCount(@Param("createdBy") String createdBy,@Param("ticketStatus") String ticketStatus);
}