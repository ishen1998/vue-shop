package com.shop.module.ticketManager.service;

import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.ticketManager.entity.TicketCustomerR;

import java.util.List;
import java.util.Map;

public interface TicketManagerService {
    int deleteCustomerTicket(String id) throws CustomizeExp;

    int insert(TicketCustomerR ticketCustomerR);

    Map selectByCustomerMobile(String mobile,Integer current,Integer size) throws CustomizeExp;

    TicketCustomerR customerGetTicket(String cLoginName, String ticketId) throws CustomizeExp;
}
