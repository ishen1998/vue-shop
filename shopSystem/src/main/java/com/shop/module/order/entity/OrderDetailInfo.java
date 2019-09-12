package com.shop.module.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * order_detail_info
 * @author 
 */
public class OrderDetailInfo implements Serializable {
    /**
     * 订单明细主键id
     */
    private String orderDetailId;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单明细编号
     */
    private String orderNo;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品图片
     */
    private String itemImg;

    /**
     * 商品状态："Y"上架、“N”下架
     */
    private String itemStatus;

    /**
     * 购买数量
     */
    private Integer itemNum;

    /**
     * 商品金额
     */
    private BigDecimal itemPrice;

    /**
     * 商品总金额
     */
    private BigDecimal priceTotal;

    /**
     * 客户id
     */
    private String cId;

    /**
     * 客户登录名称
     */
    private String cLoginName;


    private static final long serialVersionUID = 1L;


    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailInfo that = (OrderDetailInfo) o;

        if (!orderDetailId.equals(that.orderDetailId)) return false;
        if (!revision.equals(that.revision)) return false;
        if (!createdBy.equals(that.createdBy)) return false;
        if (!createdTime.equals(that.createdTime)) return false;
        if (!updatedBy.equals(that.updatedBy)) return false;
        if (!updatedTime.equals(that.updatedTime)) return false;
        if (!orderId.equals(that.orderId)) return false;
        if (!orderNo.equals(that.orderNo)) return false;
        if (!itemId.equals(that.itemId)) return false;
        if (!itemName.equals(that.itemName)) return false;
        if (!itemImg.equals(that.itemImg)) return false;
        if (!itemNum.equals(that.itemNum)) return false;
        if (!itemPrice.equals(that.itemPrice)) return false;
        if (!priceTotal.equals(that.priceTotal)) return false;
        if (!cId.equals(that.cId)) return false;
        if (!cLoginName.equals(that.cLoginName)) return false;
        return itemStatus.equals(that.itemStatus);
    }

    @Override
    public int hashCode() {
        int result = orderDetailId.hashCode();
        result = 31 * result + revision.hashCode();
        result = 31 * result + createdBy.hashCode();
        result = 31 * result + createdTime.hashCode();
        result = 31 * result + updatedBy.hashCode();
        result = 31 * result + updatedTime.hashCode();
        result = 31 * result + orderId.hashCode();
        result = 31 * result + orderNo.hashCode();
        result = 31 * result + itemId.hashCode();
        result = 31 * result + itemName.hashCode();
        result = 31 * result + itemImg.hashCode();
        result = 31 * result + itemStatus.hashCode();
        result = 31 * result + itemNum.hashCode();
        result = 31 * result + itemPrice.hashCode();
        result = 31 * result + priceTotal.hashCode();
        result = 31 * result + cId.hashCode();
        result = 31 * result + cLoginName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderDetailInfo{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", revision=" + revision +
                ", createdBy='" + createdBy + '\'' +
                ", createdTime=" + createdTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime=" + updatedTime +
                ", orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemImg='" + itemImg + '\'' +
                ", itemNum=" + itemNum +
                ", itemPrice=" + itemPrice +
                ", priceTotal=" + priceTotal +
                ", cId='" + cId + '\'' +
                ", cLoginName='" + cLoginName + '\'' +
                ", itemStatus=" + itemStatus +
                '}';
    }
}