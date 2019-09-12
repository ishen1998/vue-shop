package com.shop.mbg;

import com.shop.module.order.service.OrderDetailInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MbgApplicationTests {

    @Autowired
    private OrderDetailInfoService orderDetailInfoService;


    @Test
    public void a() {
        System.out.println(orderDetailInfoService.updateByItemId("0587c8134cb0461f84b3f19038361110", "Y"));
    }

}
