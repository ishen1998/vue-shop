package com.shop.module.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.result.CommonResult;
import com.shop.module.admin.util.SpringSecurityUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.entity.OrderInfo;
import com.shop.module.order.entity.OrderParam;
import com.shop.module.order.service.OrderInfoService;
import com.shop.module.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 28166
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderService orderService;


    @ApiOperation("订单分析")
    @GetMapping(value = "/orderAnalyzing")
    public CommonResult orderAnalyzing(Long beginTime) {
        Map<String, Object> map = orderService.orderAnalyzing(beginTime);
        return CommonResult.success(map);
    }

    @ApiOperation("根据订单ID查询订单明细页标题")
    @GetMapping(value = "/queryOrderDetailTitle")
    public CommonResult queryOrderDetailTitle(String orderId) {
        OrderInfo orderInfo = orderInfoService.queryOrderDetailTitle(orderId);
        if (orderInfo == null) {
            return CommonResult.success(null,"未找该到订单");
        }
        return CommonResult.success(orderInfo);
    }

    @ApiOperation("根据条件查询订单")
    @GetMapping(value = "/queryOrder")
    public CommonResult queryOrder(@RequestParam(defaultValue = "1") Integer current,
                                   @RequestParam(defaultValue = "5") Integer size,
                                   String orderNo, String startTime, String endTime, String payStatus) {
        Page<OrderInfo> order = new Page<>(current, size);
        List<OrderInfo> orderPage = orderInfoService.queryOrder(order, orderNo, startTime, endTime, payStatus);
        Map<String, Object> result = new HashMap<>(2);
        result.put("orderInfo", orderPage);
        result.put("count", order.getTotal());
        if (orderPage.size() == 0) {
            return CommonResult.success(result,"未找到订单");
        }
        return CommonResult.success(result);
    }

    @ApiOperation("分页查询所有订单")
    @GetMapping(value = "/queryOrderAll")
    public CommonResult queryOrderAll(@RequestParam(defaultValue = "1") Integer current,
                                      @RequestParam(defaultValue = "5") Integer size) {
        Page<OrderInfo> order = new Page<>(current, size);
        List<OrderInfo> orderPage = orderInfoService.queryOrderAll(order);
        Map<String, Object> result = new HashMap<>(2);
        result.put("orderInfo", orderPage);
        result.put("count", order.getTotal());
        return CommonResult.success(result);
    }

    @ApiOperation("根据客户Id查询订单")
    @GetMapping(value = "/queryOrderBycId")
    public CommonResult queryOrderBycId(String cId) {
        List<OrderInfo> orderInfos = orderInfoService.queryOrderBycId(cId);
        if (orderInfos.size() == 0) {
            return CommonResult.success(orderInfos, "该客户没有订单");
        }
        return CommonResult.success(orderInfos);
    }

    @ApiOperation("根据订单状态和支付状态查询订单")
    @GetMapping(value = "/queryByState")
    public CommonResult queryByState(String cId, String orderStatus, String payStatus, String receiptStatus) {
        List<OrderInfo> order = orderInfoService.queryByState(cId, orderStatus, payStatus, receiptStatus);
        if (order.size() == 0) {
            return CommonResult.success(order, "暂无订单");
        }
        return CommonResult.success(order);
    }

    @ApiOperation("根据订单ID删除订单")
    @DeleteMapping(value = "/deleteByOrderId/{orderId}")
    public CommonResult deleteByOrderId(@PathVariable String orderId) throws CustomizeExp {
        int order = orderInfoService.deleteByPrimaryKey(orderId);
        return CommonResult.success(order, "删除成功");
    }

    @ApiOperation("根据订单ID批量删除订单")
    @DeleteMapping(value = "/deleteByOrderIds")
    public CommonResult deleteByOrderIds(@RequestBody List<String> orderIds) throws CustomizeExp {
        int order = orderInfoService.deleteByPrimaryKeys(orderIds);
        return CommonResult.success(order, "删除成功");
    }

    @ApiOperation("根据订单ID修改订单")
    @PutMapping(value = "/updateByOrderId")
    public CommonResult updateByOrderId(OrderInfo record) {
        record.setUpdateBy(new SpringSecurityUtil().currentUserName());
        record.setUpdateTime(new Date());
        int order = orderInfoService.updateByPrimaryKeySelective(record);
        return CommonResult.success(order, "修改成功");
    }

    @ApiOperation("生成订单")
    @PostMapping(value = "/insertOrder")
    public CommonResult insertOrder(@RequestBody OrderParam record) throws CustomizeExp {
        Map<String, Object> result = orderService.generateOrder(record);
        return CommonResult.success(result, "生成订单成功！");
    }

    @ApiOperation("订单支付")
    @PutMapping(value = "/paySucceed")
    public CommonResult paySucceed(String orderId) throws CustomizeExp {
        int order = orderService.paySuccess(orderId);
        return CommonResult.success(order, "支付成功");
    }

    @ApiOperation("合并支付")
    @PutMapping(value = "/payTogether")
    public CommonResult payTogether(@RequestBody List<String> orderIds) throws CustomizeExp {
        int order = orderService.payTogether(orderIds);
        return CommonResult.success(order, "支付成功");
    }

    @ApiOperation("确认收货")
    @PutMapping(value = "/confirmOrder")
    public CommonResult confirmOrder(String orderId) throws CustomizeExp {
        int order = orderService.confirmOrder(orderId);
        return CommonResult.success(order, "收货成功");
    }

    @ApiOperation("退货")
    @PutMapping(value = "/recedeOrder")
    public CommonResult recedeOrder(String orderId) throws CustomizeExp {
        int order = orderService.recedeOrder(orderId);
        return CommonResult.success(order, "退货成功");
    }

    @ApiOperation("客户取消订单")
    @PutMapping(value = "/cancelOrder")
    public CommonResult cancelOrder(String orderId) throws CustomizeExp {
        int order = orderService.cancelOrder(orderId);
        return CommonResult.success(order, "取消订单成功");
    }

    @ApiOperation("自动取消超时订单")
    @PutMapping(value = "/cancelTimeOutOrder")
    public CommonResult cancelTimeOutOrder() throws CustomizeExp {
        orderService.cancelTimeOutOrder();
        return CommonResult.success(null);
    }

    @ApiOperation("自动确认订单")
    @PutMapping(value = "/confirmedOrder")
    public CommonResult confirmedOrder() {
        orderService.confirmedOrder();
        return CommonResult.success(null);
    }
}

