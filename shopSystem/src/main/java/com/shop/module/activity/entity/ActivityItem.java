package com.shop.module.activity.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityItem implements Serializable {
    private String activityItemId;
    private String activityName;
    private String activityImg;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String activityId;
    private String itemId;
    private String activityPrice;
    private String price;
}