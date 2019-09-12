package com.shop.module.activity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActivityVo implements Serializable {

    private ActivityInfo activityInfo;
    private List<ActivityItem> activityItem;
}
