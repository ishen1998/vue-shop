package com.shop.module.activity.entity;

import com.shop.module.shopmessage.entity.ItemInfo;
import lombok.Data;

import java.io.Serializable;
@Data
public class ActivityInfoAndItem implements Serializable {
    private ActivityInfo activityInfo;
    private ItemInfo itemInfo;
}
