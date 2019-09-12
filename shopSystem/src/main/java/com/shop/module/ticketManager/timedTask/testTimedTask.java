package com.shop.module.ticketManager.timedTask;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.module.ticketManager.dao.TicketCustomerRDAO;
import com.shop.module.ticketManager.dao.TicketInfoDAO;
import com.shop.module.ticketManager.entity.TicketCustomerR;
import com.shop.module.ticketManager.entity.TicketInfo;
import com.shop.module.ticketManager.service.TicketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class testTimedTask {
    @Autowired
    private TicketInfoDAO ticketInfoDAO;
    
    @Autowired
    private TicketCustomerRDAO ticketCustomerRDAO;

    /**
     * 每天凌晨进行活动结束判断更新
     * **/
    @Scheduled(cron = "0 0 0 * * ?")
    private void configureTasks() {
        List<TicketInfo> ticketInfos = ticketInfoDAO.selectAllTicket();
        for (TicketInfo ticketInfo:ticketInfos){
            String ticketId= ticketInfo.getTicketId();
            String ticketStatus = ticketInfo.getTicketStatus();
            String ticketStatus1="";
            if(new Date().before(ticketInfo.getEndTime())){
                ticketStatus1=ticketStatus;
            }else{
                ticketStatus1="N";
            }
            ticketInfoDAO.updateOnlyTicketStatus(ticketId,ticketStatus1);
        }
    }


    /**
     * 更新客户卡劵状态
     * */
    @Scheduled(cron = "59 59 23 ? * *") //每一分钟的59秒进行操作
    public void configureTasks2() {
        List<TicketCustomerR> ticketCustomerRS = ticketCustomerRDAO.selectAllCustomerTic();
        String  useStatus="";
        for (TicketCustomerR ticketCustomerR:ticketCustomerRS){
            String useStatus1 = ticketCustomerR.getUseStatus();
            if(new Date().after(ticketCustomerR.getTicketInfo().getEndTime())){
                useStatus="3";
            }  else {
                useStatus = useStatus1;
            }
            String ticketCustomerId= ticketCustomerR.getTicketCustomerId();
            ticketCustomerRDAO.updateTicCusStatus(ticketCustomerId,useStatus);
        }
    }

}
