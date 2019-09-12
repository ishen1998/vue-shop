package com.shop.module.order.util;

import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 28166
 */
@Component
@Lazy(value = false)
public class OrderTimeOutCancelTask {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder() throws CustomizeExp {
        orderService.cancelTimeOutOrder();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void confirmedOrder(){
        orderService.confirmedOrder();
    }
}
