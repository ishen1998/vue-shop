package com.shop.module.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.result.CommonResult;
import com.shop.module.order.service.OrderDetailInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 28166
 */
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailInfoController {

    @Autowired
    private OrderDetailInfoService orderDetailInfoService;

    @ApiOperation("根据订单ID查询订单明细页内容")
    @GetMapping(value = "/queryOrderDetail")
    public CommonResult queryOrderDetail(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "5") Integer size, String orderId) {
        return CommonResult.success(orderDetailInfoService.queryOrderDetail(new Page<>(current, size), orderId));
    }
}
