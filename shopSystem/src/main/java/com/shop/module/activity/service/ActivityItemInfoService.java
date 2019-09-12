package com.shop.module.activity.service;


import com.shop.common.CommonResult;
import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.activity.entity.ActivityInfoAndItem;
import com.shop.module.activity.entity.ActivityItem;
import com.shop.module.activity.entity.ActivityVo;
import com.shop.module.shopmessage.entity.ItemInfo;


import java.util.List;
import java.util.Map;

public interface ActivityItemInfoService {
    CommonResult insert(ActivityItem activityItem);

    CommonResult select(String activityId);

    int selectNum(String activityId, String itemName);

    List<ItemInfo> selectActivityItem(String activityId, String itemName, String msg, int current, int size);

    List<ItemInfo> selectAllItem(String msg, int current, int size);

    List<ActivityInfo> selectActivity(int current, int size, String activityId);

    Map<String, Object> selectHomeActivity();

    Map<String, Object> selectHomeActivityByName(String activityName);

    Integer selectCount();

    int deleteAll(String activityId);

    CommonResult insertAll(ActivityVo activityVo);

    CommonResult modifyAll(ActivityVo activityVo);

    Map<String, Object> selectSort(int current, int size, String activityId, String itemName, String listParent);

    int selectSortCount(String activityId, String itemName, String listParent);

    Map<String, Object> selectItemSort(int current, int size, String itemName, String listParent);

    int selectItemSortCount(String itemName, String listParent);

    List<String> selectExists(String itemId);
}
