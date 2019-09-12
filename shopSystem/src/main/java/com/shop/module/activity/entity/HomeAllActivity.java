package com.shop.module.activity.entity;

import com.shop.module.shopmessage.entity.ItemInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/008 15:01
 */
@Getter
@Setter
public class HomeAllActivity implements Serializable {
    private ActivityInfo activityInfo;
    private List<List<ItemInfo>> itemInfos;

    public HomeAllActivity(ActivityInfo activityInfo, List<List<ItemInfo>> itemInfos) {
        this.activityInfo = activityInfo;
        this.itemInfos = itemInfos;
    }
}
