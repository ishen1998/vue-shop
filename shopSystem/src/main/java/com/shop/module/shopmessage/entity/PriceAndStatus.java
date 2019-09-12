package com.shop.module.shopmessage.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PriceAndStatus {

    private String activityPrice;
    private String activityStatus;
    private Date startTime;
    private Date endTime;

}
