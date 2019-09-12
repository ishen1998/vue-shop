package com.shop.module.order.entity;


import java.util.List;

/**
 * 生成订单时传入的参数
 * @author 28166
 */
public class OrderParam {

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
     * 购物车ID
     */
    private List<String> trolleyId;


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

    public List<String> getTrolleyId() {
        return trolleyId;
    }

    public void setTrolleyId(List<String> trolleyId) {
        this.trolleyId = trolleyId;
    }
}
