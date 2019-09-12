package com.shop.module.ticketManager.service;


import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.ticketManager.entity.TicketInfo;

import java.util.List;
import java.util.Map;


public interface TicketInfoService {
    int deleteByPrimaryKey(String id) throws CustomizeExp;

    int insert(TicketInfo ticketInfo) throws CustomizeExp;
    
    List<TicketInfo> selectAllTicket( ) throws CustomizeExp;

    TicketInfo selectByPrimaryKey(String id) throws CustomizeExp;

    int updateByPrimaryKey(TicketInfo ticketInfo) throws CustomizeExp;

     void  updateTicketStatus(String id, String ticketStatus) throws CustomizeExp;

    Map selectByCreateBy(Integer current, Integer size, String createdBy, String ticketStatus) throws CustomizeExp;

    Map ticketPageHelper(Integer current, Integer size) throws CustomizeExp;
}
