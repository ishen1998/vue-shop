package com.shop.module.ticketManager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.CommonResult;
import com.shop.common.Result;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.service.CustomerInfoService;
import com.shop.module.ticketManager.dao.TicketCustomerRDAO;
import com.shop.module.ticketManager.entity.TicketCustomerR;
import com.shop.module.ticketManager.entity.TicketInfo;
import com.shop.module.ticketManager.service.TicketManagerService;
import com.shop.module.ticketManager.service.TicketInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/ticket")
@Controller
public class TicketManagerController {

    @Autowired
    private TicketManagerService ticketManagerService;
    /**
     * 通过客户手机号查询客户卡劵信息
     * @param mobile
     * @return result
     * */
    @ResponseBody
    @RequestMapping("/selectByCustomerMobile")
    public CommonResult selectByCustomerMobile(String mobile, Integer current, Integer size) throws CustomizeExp {
        Map map=ticketManagerService.selectByCustomerMobile(mobile, current, size);
        return CommonResult.success(map);
    }

    @GetMapping("/deleteCustomerTicket")
    @ResponseBody
    public CommonResult deleteCustomerTicket(String id) throws CustomizeExp {
        ticketManagerService.deleteCustomerTicket(id);
        return CommonResult.success("", "删除成功");
    }


   /**
    *领取客户卡劵
    * */
    @ResponseBody
    @GetMapping("/customerGetTicket")
    public CommonResult customerGetTicket(String cLoginName,String ticketId) throws CustomizeExp {
        TicketCustomerR ticketCustomerR = ticketManagerService.customerGetTicket(cLoginName, ticketId);
        return CommonResult.success(ticketCustomerR,"领取成功");
    }
}
