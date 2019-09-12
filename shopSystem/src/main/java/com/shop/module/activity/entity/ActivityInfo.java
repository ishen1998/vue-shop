package com.shop.module.activity.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.module.shopmessage.entity.ItemInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ActivityInfo implements Serializable {
        private String activityName;
        private String activityImg;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String activityId;
        private int num;
        private String startTime;
        private String endTime;
        private String activityStatus;
        private String rule;
        private String preferenceItemId;
        private String preferenceId;
        private String activityItemId;
        private List<ItemInfo> itemInfos;
}
