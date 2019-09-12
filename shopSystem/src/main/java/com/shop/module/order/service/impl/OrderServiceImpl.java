package com.shop.module.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.dao.OrderDetailInfoDao;
import com.shop.module.order.dao.OrderInfoDao;
import com.shop.module.order.entity.OrderDelayed;
import com.shop.module.order.entity.OrderDetailInfo;
import com.shop.module.order.entity.OrderInfo;
import com.shop.module.order.entity.OrderParam;
import com.shop.module.order.service.OrderService;
import com.shop.module.order.util.SnowFlake;
import com.shop.module.shopcart.dao.TrolleyMapper;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;
import com.shop.module.shopmessage.entity.ItemInfo;
import com.shop.module.shopmessage.entity.PriceAndStatus;
import com.shop.module.shopmessage.service.ItemInfoService;
import com.shop.module.ticketManager.dao.TicketCustomerRDAO;
import com.shop.module.ticketManager.dao.TicketInfoDAO;
import com.shop.module.ticketManager.entity.TicketCustomerR;
import com.shop.module.ticketManager.entity.TicketInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author 28166
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private OrderDetailInfoDao orderDetailInfoDao;

    @Autowired
    private ItemInfoService itemInfoService;

    @Autowired
    private TicketCustomerRDAO ticketCustomerrDao;

    @Autowired
    private TicketInfoDAO ticketInfoDAO;

    @Autowired
    private TrolleyMapper trolleyMapper;


    /**
     * 生成ID
     */
    private String generateOrderId() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 计算商品价格
     */
    private BigDecimal countItemPrice(String itemId) throws CustomizeExp {
        ItemInfo itemInfo = itemInfoService.selectByPrimaryKey(itemId);
        BigDecimal price = itemInfo.getPrice();
        Date date = new Date();
        if (itemInfo.getPriceAndStatuses().size() != 0) {
            PriceAndStatus priceAndStatus = itemInfo.getPriceAndStatuses().get(0);
            // 活动状态
            Boolean isStatus = "Y".equals(priceAndStatus.getActivityStatus());
            // 活动日期
            Boolean isDate = date.after(priceAndStatus.getStartTime()) && date.before(priceAndStatus.getEndTime());
            if (isStatus && isDate) {
                // 参与活动商品价格
                price = new BigDecimal(itemInfo.getPriceAndStatuses().get(0).getActivityPrice());
            }
        }
        return price;
    }

    /**
     * 计算商品合计
     * @param orderDetailInfo 商品信息
     */
    private BigDecimal priceTotal(OrderDetailInfo orderDetailInfo) {
        return orderDetailInfo.getItemPrice().multiply(new BigDecimal(orderDetailInfo.getItemNum()));
    }

    /**
     * 计算订单总金额
     * @param orderDetailInfos 商品信息集合
     */
    private BigDecimal totalAmount(List<OrderDetailInfo> orderDetailInfos) {
        return orderDetailInfos.stream()
                .map(OrderDetailInfo::getPriceTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 计算商品总数量
     * @param orderDetailInfos 商品信息集合
     */
    private Integer countNum(List<OrderDetailInfo> orderDetailInfos) {
        Integer num = 0;
        for (OrderDetailInfo orderDetailInfo : orderDetailInfos) {
            num += orderDetailInfo.getItemNum();
        }
        return num;
    }

    /**
     * 判断下单商品是否都有库存
     * @param trolleyInfos 购物车信息
     */
    private Boolean isStock(List<TrolleyInfoEntity> trolleyInfos) throws CustomizeExp {
        for (TrolleyInfoEntity trolleyInfo : trolleyInfos) {
            ItemInfo itemInfo = itemInfoService.selectByPrimaryKey(trolleyInfo.getItemId());
            // 商品库存
            Integer stock = itemInfo.getNum();
            // 商品购买数量
            Integer num = trolleyInfo.getNum();
            if (num > stock) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算商品库存
     * @param orderDetailInfo 商品信息
     * @param orderStatus     订单状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void countStock(OrderDetailInfo orderDetailInfo, String orderStatus) throws CustomizeExp {
        String itemId = orderDetailInfo.getItemId();
        ItemInfo item = itemInfoService.selectByPrimaryKey(itemId);
        // 商品库存
        Integer stock = item.getNum();
        // 商品购买数量
        Integer num = orderDetailInfo.getItemNum();
        // “0”状态扣减库存，“2”和“3”状态则返回库存
        stock = "0".equals(orderStatus) ? stock - num : stock + num;
        item.setItemId(itemId);
        item.setNum(stock);
        itemInfoService.updateByPrimaryKeySelective(item);
    }

    /**
     * 判断卡卷是否可用
     * @param ticketUserId 购物车ID
     */
    private Map<String, Object> isCoupon(String ticketUserId, BigDecimal amount) throws CustomizeExp {
        boolean isCoupon = true;
        // 客户卡卷
        TicketCustomerR ticketCustomer = ticketCustomerrDao.selectCusTicketById(ticketUserId);
        if (ticketCustomer == null) {
            throw new CustomizeExp("未找到该卡卷");
        }
        // 卡卷
        TicketInfo ticketInfo = ticketInfoDAO.selectByPrimaryKey(ticketCustomer.getTicketId());
        if (ticketInfo == null) {
            throw new CustomizeExp("未找到该卡卷");
        }
        String[] split = ticketInfo.getTicketRule().split(",");
        // 满减金额
        BigDecimal fullPrice = new BigDecimal(split[0]);
        // 卡卷金额
        BigDecimal reductionPrice = new BigDecimal(split[1]);
        // 卡卷被禁用不可使用
        if ("N".equals(ticketInfo.getTicketStatus())) {
            isCoupon = false;
        }
        // 订单总金额不满足卡卷条件不可使用
        if (amount.compareTo(fullPrice) == -1) {
            isCoupon = false;
        }
        // 当前时间不在卡卷使用期间不可使用
        Date date = new Date();
        if (!(date.after(ticketInfo.getStartTime()) && date.before(ticketInfo.getEndTime()))) {
            isCoupon = false;
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("isCoupon", isCoupon);
        map.put("reductionPrice", reductionPrice);
        return map;
    }

    /**
     * 判断下单商品是否有下架
     * @param trolleyInfos 购物车信息
     */
    private Boolean isItemStatus(List<TrolleyInfoEntity> trolleyInfos) throws CustomizeExp {
        for (TrolleyInfoEntity trolleyInfo : trolleyInfos) {
            ItemInfo itemInfo = itemInfoService.selectByPrimaryKey(trolleyInfo.getItemId());
            // 判断该商品是否下架
            if ("N".equals(itemInfo.getIsShow())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断商品列表是否有下单商品
     * @param trolleyInfos 购物车信息
     */
    private Boolean isItem(List<TrolleyInfoEntity> trolleyInfos) throws CustomizeExp {
        for (TrolleyInfoEntity trolleyInfo : trolleyInfos) {
            ItemInfo itemInfo = itemInfoService.selectByPrimaryKey(trolleyInfo.getItemId());
            // 判断是否存在该商品
            if (itemInfo == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成订单
     * @param record 生成订单传入的参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> generateOrder(OrderParam record) throws CustomizeExp {
        String orderId = generateOrderId();
        String orderNo = String.valueOf(SnowFlake.nextId());
        Date time = new Date();
        String cId = record.getcId();
        String cLogName = record.getcLoginName();
        String ticketUserId = record.getTicketId();
        // 从购物车获得下单商品信息
        List<TrolleyInfoEntity> trolleyInfoEntities = trolleyMapper.selectBatchIds(record.getTrolleyId());
        // 判断商品列表是否有下单商品
        if (!isItem(trolleyInfoEntities)) {
            throw new CustomizeExp("暂无该商品");
        }
        // 判断下单商品是否有下架
        if (!isItemStatus(trolleyInfoEntities)) {
            throw new CustomizeExp("存在已下架商品，无法下单");
        }
        // 判断下单商品是否都有库存
        if (!isStock(trolleyInfoEntities)) {
            throw new CustomizeExp("库存不足，无法下单");
        }
        // 生成下单商品信息
        List<OrderDetailInfo> orderDetailInfoList = new ArrayList<>();
        for (TrolleyInfoEntity trolleyInfo : trolleyInfoEntities) {

            OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
            ItemInfo itemInfo = itemInfoService.selectByPrimaryKey(trolleyInfo.getItemId());

            orderDetailInfo.setOrderDetailId(generateOrderId());
            orderDetailInfo.setOrderId(orderId);
            orderDetailInfo.setOrderNo(orderNo);
            orderDetailInfo.setCreatedBy(cLogName);
            orderDetailInfo.setUpdatedBy(cLogName);
            orderDetailInfo.setCreatedTime(time);
            orderDetailInfo.setUpdatedTime(time);
            orderDetailInfo.setcId(cId);
            orderDetailInfo.setcLoginName(cLogName);
            orderDetailInfo.setItemId(trolleyInfo.getItemId());
            orderDetailInfo.setItemName(trolleyInfo.getItemName());
            orderDetailInfo.setItemNum(trolleyInfo.getNum());
            orderDetailInfo.setItemImg(itemInfo.getCoverImg());
            orderDetailInfo.setItemStatus(itemInfo.getIsShow());
            orderDetailInfo.setItemPrice(countItemPrice(trolleyInfo.getItemId()));
            orderDetailInfo.setPriceTotal(priceTotal(orderDetailInfo));

            orderDetailInfoList.add(orderDetailInfo);
            countStock(orderDetailInfo, "0");
        }
        // 生成订单信息
        OrderInfo orderInfo = new OrderInfo();
        // 订单总金额
        BigDecimal amount = totalAmount(orderDetailInfoList);
        // 订单商品总数量
        Integer num = countNum(orderDetailInfoList);
        // 卡卷金额
        BigDecimal couponPrice;

        // 判断是否使用了卡卷
        if (ticketUserId != null && !ticketUserId.isEmpty()) {
            Map<String, Object> coupon = isCoupon(ticketUserId, amount);
            // 判断卡卷是否可用
            if ((Boolean) coupon.get("isCoupon")) {
                // 获取卡卷金额
                couponPrice = (BigDecimal) coupon.get("reductionPrice");
                // 修改客户卡卷为已使用
                ticketCustomerrDao.updateTicCusStatus(ticketUserId, "2");
            } else {
                throw new CustomizeExp("该卡卷不可用");
            }
        } else {
            couponPrice = new BigDecimal(0);
        }
        // 实付金额
        BigDecimal payAmount = amount.subtract(couponPrice);

        orderInfo.setOrderId(orderId);
        orderInfo.setOrderNo(orderNo);
        orderInfo.setCreateBy(cLogName);
        orderInfo.setUpdateBy(cLogName);
        orderInfo.setcId(cId);
        orderInfo.setcLoginName(cLogName);
        orderInfo.setCreateTime(time);
        orderInfo.setUpdateTime(time);
        // 订单状态：0、进行中；1、订单完成；2、订单取消；3、订单退货
        orderInfo.setOrderStatus("0");
        // 支付状态:0、未支付；1、已支付
        orderInfo.setPayStatus("0");
        // 收货信息
        orderInfo.setConsignee(record.getConsignee());
        orderInfo.setConsigneeMobile(record.getConsigneeMobile());
        orderInfo.setConsigneeAddress(record.getConsigneeAddress());
        // 订单信息
        orderInfo.setTotalAmount(amount);
        orderInfo.setNumTotal(num);
        orderInfo.setCouponAmount(couponPrice);
        orderInfo.setPayAmount(payAmount);
        orderInfo.setTicketId(record.getTicketId());
        // 插入订单和订单详情
        orderDetailInfoDao.insertOrderList(orderDetailInfoList);
        orderInfoDao.insertSelective(orderInfo);

        OrderDelay.getInstance().init();
        ExecutorService service = Executors.newFixedThreadPool(10);
        Runnable run = () -> {
            OrderDelayed orderDelayed = new OrderDelayed();
            orderDelayed.setOrderId(orderInfo.getOrderId());
            orderDelayed.setPayStatus(orderInfo.getOrderStatus());
            orderDelayed.setExpire(System.currentTimeMillis() + 1000 * 60 * 30);
            OrderDelay.getInstance().orderPutQueue(orderDelayed);
        };
        service.execute(run);

        Map<String, Object> result = new HashMap<>(2);
        result.put("orderInfo", orderInfo);
        result.put("orderDetailInfo", orderDetailInfoList);
        return result;
    }

    /**
     * 订单支付
     * @param orderId 订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int paySuccess(String orderId) throws CustomizeExp {
        OrderInfo order = new OrderInfo();
        order.setOrderId(orderId);
        order.setPayStatus("1");
        // 收货状态：0、待收货；1、已收货
        order.setReceiptStatus("0");
        order.setPayTime(new Date());
        int o = orderInfoDao.updateByPrimaryKeySelective(order);
        if (o == 0) {
            throw new CustomizeExp("支付异常");
        }
        return o;
    }

    /**
     * 合并支付
     * @param orderIds 多个订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int payTogether(List<String> orderIds) throws CustomizeExp {
        int i = orderInfoDao.updateStatusByOrderIds(orderIds, "1", "0");
        if (i == 0) {
            throw new CustomizeExp("支付异常");
        }
        return i;
    }

    /**
     * 取消订单
     * @param orderId 订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int cancelOrder(String orderId) throws CustomizeExp {
        OrderInfo order = new OrderInfo();
        order.setOrderId(orderId);
        order.setOrderStatus("2");
        int o = orderInfoDao.updateByPrimaryKeySelective(order);
        if (o == 0) {
            throw new CustomizeExp("取消订单异常");
        }
        // 查询该订单的所有商品并返回商品库存
        List<OrderDetailInfo> orderDetailInfos = orderDetailInfoDao.queryOrderDetail(orderId);
        for (OrderDetailInfo orderDetailInfo : orderDetailInfos) {
            countStock(orderDetailInfo, "2");
        }
        return o;
    }

    /**
     * 确认收货
     * @param orderId 订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int confirmOrder(String orderId) throws CustomizeExp {
        OrderInfo order = new OrderInfo();
        order.setOrderId(orderId);
        order.setReceiptStatus("1");
        order.setOrderStatus("1");
        int o = orderInfoDao.updateByPrimaryKeySelective(order);
        if (o == 0) {
            throw new CustomizeExp("确认收货异常");
        }
        return o;
    }

    /**
     * 退款/退货
     * @param orderId 订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int recedeOrder(String orderId) throws CustomizeExp {
        OrderInfo order = new OrderInfo();
        order.setOrderId(orderId);
        order.setOrderStatus("3");
        int o = orderInfoDao.updateByPrimaryKeySelective(order);
        if (o == 0) {
            throw new CustomizeExp("退货异常");

        }
        // 查询该订单的所有商品并返回商品库存
        List<OrderDetailInfo> orderDetailInfos = orderDetailInfoDao.queryOrderDetail(orderId);
        for (OrderDetailInfo orderDetailInfo : orderDetailInfos) {
            countStock(orderDetailInfo, "3");
        }
        return o;
    }

    /**
     * 订单分析
     * @param beginTime 开始时间
     */
    @Override
    public Map<String, Object> orderAnalyzing(Long beginTime) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(beginTime);
        Map<String, Object> map = new HashMap<>(4);
        // 进行的订单
        List<JSONObject> underwayOrder = orderInfoDao.queryOrderData(currentTime, "0");
        // 完成的订单
        List<JSONObject> finishOrder = orderInfoDao.queryOrderData(currentTime, "1");
        // 取消的订单
        List<JSONObject> cancelOrder = orderInfoDao.queryOrderData(currentTime, "2");
        // 退货的订单
        List<JSONObject> returnOrder = orderInfoDao.queryOrderData(currentTime, "3");
        map.put("0", underwayOrder);
        map.put("1", finishOrder);
        map.put("2", cancelOrder);
        map.put("3", returnOrder);
        return map;
    }

    /**
     * 自动取消未支付超时订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelTimeOutOrder() throws CustomizeExp {
        // 查询未支付超时订单
        List<OrderInfo> orderInfos = orderInfoDao.queryOverTimeOrder(30);
        if (!CollectionUtils.isEmpty(orderInfos)) {
            List<String> list = new ArrayList<>();
            for (OrderInfo orderInfo : orderInfos) {
                list.add(orderInfo.getOrderId());
            }
            // 返回库存
            for (OrderInfo orderInfo : orderInfos) {
                List<OrderDetailInfo> orderDetailInfos = orderDetailInfoDao.queryOrderDetail(orderInfo.getOrderId());
                for (OrderDetailInfo orderDetailInfo : orderDetailInfos) {
                    countStock(orderDetailInfo, "2");
                }
            }
            orderInfoDao.updateOrderStatus(list, "2", null);
        }
    }

    /**
     * 自动确认收货订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmedOrder() {
        // 查询已支付未确认订单
        List<OrderInfo> orderInfos = orderInfoDao.queryUnconfirmed(7);
        if (!CollectionUtils.isEmpty(orderInfos)) {
            List<String> list = new ArrayList<>();
            for (OrderInfo orderInfo : orderInfos) {
                list.add(orderInfo.getOrderId());
            }
            orderInfoDao.updateOrderStatus(list, "1", "1");
        }
    }
}
