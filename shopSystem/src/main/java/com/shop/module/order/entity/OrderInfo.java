package com.shop.module.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * order_info
 * @author
 */
public class OrderInfo implements Serializable {


    /**
     * 订单id
     */
    private String orderId;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收货人手机号
     */
    private String consigneeMobile;

    /**
     * 收货人地址
     */
    private String consigneeAddress;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 订单状态：0、进行中；1、订单完成；2、订单取消；3、订单退货
     */
    private String orderStatus;

    /**
     * 支付状态:0、未支付；1、已支付
     */
    private String payStatus;

    /**
     * 收货状态：0、待收货；1、已收货
     */
    private String receiptStatus;

    /**
     * 客户主键id
     */
    private String cId;

    /**
     * 客户登录名称
     */
    private String cLoginName;

    /**
     * 卡卷ID
     */
    private String ticketId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal couponAmount;

    /**
     * 商品数量总计
     */

    private Integer numTotal;

    /**
     * 订单明细页
     */
    private List<OrderDetailInfo> orderDetailInfos;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcLoginName() {
        return cLoginName;
    }

    public void setcLoginName(String cLoginName) {
        this.cLoginName = cLoginName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getNumTotal() {
        return numTotal;
    }

    public void setNumTotal(Integer numTotal) {
        this.numTotal = numTotal;
    }

    public List<OrderDetailInfo> getOrderDetailInfos() {
        return orderDetailInfos;
    }

    public void setOrderDetailInfos(List<OrderDetailInfo> orderDetailInfos) {
        this.orderDetailInfos = orderDetailInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderInfo orderInfo = (OrderInfo) o;

        if (!orderId.equals(orderInfo.orderId)) return false;
        if (!revision.equals(orderInfo.revision)) return false;
        if (!createBy.equals(orderInfo.createBy)) return false;
        if (!createTime.equals(orderInfo.createTime)) return false;
        if (!updateBy.equals(orderInfo.updateBy)) return false;
        if (!updateTime.equals(orderInfo.updateTime)) return false;
        if (!orderNo.equals(orderInfo.orderNo)) return false;
        if (!consignee.equals(orderInfo.consignee)) return false;
        if (!consigneeMobile.equals(orderInfo.consigneeMobile)) return false;
        if (!consigneeAddress.equals(orderInfo.consigneeAddress)) return false;
        if (!payTime.equals(orderInfo.payTime)) return false;
        if (!orderStatus.equals(orderInfo.orderStatus)) return false;
        if (!payStatus.equals(orderInfo.payStatus)) return false;
        if (!receiptStatus.equals(orderInfo.receiptStatus)) return false;
        if (!cId.equals(orderInfo.cId)) return false;
        if (!cLoginName.equals(orderInfo.cLoginName)) return false;
        if (!ticketId.equals(orderInfo.ticketId)) return false;
        if (!totalAmount.equals(orderInfo.totalAmount)) return false;
        if (!payAmount.equals(orderInfo.payAmount)) return false;
        if (!couponAmount.equals(orderInfo.couponAmount)) return false;
        if (!numTotal.equals(orderInfo.numTotal)) return false;
        return orderDetailInfos.equals(orderInfo.orderDetailInfos);
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + revision.hashCode();
        result = 31 * result + createBy.hashCode();
        result = 31 * result + createTime.hashCode();
        result = 31 * result + updateBy.hashCode();
        result = 31 * result + updateTime.hashCode();
        result = 31 * result + orderNo.hashCode();
        result = 31 * result + consignee.hashCode();
        result = 31 * result + consigneeMobile.hashCode();
        result = 31 * result + consigneeAddress.hashCode();
        result = 31 * result + payTime.hashCode();
        result = 31 * result + orderStatus.hashCode();
        result = 31 * result + payStatus.hashCode();
        result = 31 * result + receiptStatus.hashCode();
        result = 31 * result + cId.hashCode();
        result = 31 * result + cLoginName.hashCode();
        result = 31 * result + ticketId.hashCode();
        result = 31 * result + totalAmount.hashCode();
        result = 31 * result + payAmount.hashCode();
        result = 31 * result + couponAmount.hashCode();
        result = 31 * result + numTotal.hashCode();
        result = 31 * result + orderDetailInfos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId='" + orderId + '\'' +
                ", revision=" + revision +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", orderNo='" + orderNo + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consigneeMobile='" + consigneeMobile + '\'' +
                ", consigneeAddress='" + consigneeAddress + '\'' +
                ", payTime=" + payTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", receiptStatus='" + receiptStatus + '\'' +
                ", cId='" + cId + '\'' +
                ", cLoginName='" + cLoginName + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", totalAmount=" + totalAmount +
                ", payAmount=" + payAmount +
                ", couponAmount=" + couponAmount +
                ", numTotal=" + numTotal +
                ", orderDetailInfos=" + orderDetailInfos +
                '}';
    }
}