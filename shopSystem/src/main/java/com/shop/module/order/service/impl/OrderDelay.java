package com.shop.module.order.service.impl;


import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.entity.OrderDelayed;
import com.shop.module.order.entity.OrderInfo;
import com.shop.module.order.service.OrderInfoService;
import com.shop.module.order.service.OrderService;
import com.shop.module.order.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.DelayQueue;


public class OrderDelay {

    @Autowired
    private OrderService orderService = SpringContextUtils.getBean(OrderService.class);

    @Autowired
    private OrderInfoService orderInfoService = SpringContextUtils.getBean(OrderInfoService.class);


    private static volatile OrderDelay orderDelay = null;

    private OrderDelay() {

    }

    /**
     * 单例模式，双检查锁模式，在并发环境下对象只被初始化一次
     */
    public static OrderDelay getInstance() {
        if (orderDelay == null) {
            synchronized (OrderDelay.class) {
                orderDelay = new OrderDelay();
            }
        }
        return orderDelay;
    }

    /**
     * 守护线程
     */
    private Thread mainThread;

    /**
     * 启动方法
     */
    public void init() {
        mainThread = new Thread(() -> execute());
        mainThread.setDaemon(true);
        mainThread.setName("守护线程-->");
        mainThread.start();
    }

    /**
     * 创建空延时队列
     */
    private DelayQueue<OrderDelayed> queue = new DelayQueue<>();

    /**
     * 读取延时队列，关闭超时订单
     */
    private void execute() {
        while (true) {
            try {
                if (queue.size() > 0) {
                    //从队列里获取超时的订单
                    OrderDelayed orderDelayed = queue.take();
                    String orderId = orderDelayed.getOrderId();
                    OrderInfo orderInfo = orderInfoService.selectByPrimaryKey(orderId);
                    if (orderInfo == null) {
                        System.out.println("该订单以被删除");
                    } else {
                        if ("0".equals(orderInfo.getPayStatus())) {
                            // 取消订单
                            orderService.cancelOrder(orderId);
                        } else {
                            // 从队列中移除已支付的订单
                            queue.remove(orderDelayed);
                        }
                    }
                }
            } catch (InterruptedException | CustomizeExp e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 插入订单到超时队列中
     */
    public void orderPutQueue(OrderDelayed orderDelayed) {
        queue.add(orderDelayed);
    }
}
