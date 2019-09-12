package com.shop.module.shopmessage.entity;

import lombok.Getter;
import lombok.Setter;


/**
 * @author YeLei
 */
@Getter
@Setter
public class ItemStatus {
    private String itemId;
    private String isShow;
    private String orderId;
    private String orderStatus;

}
