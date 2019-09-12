package com.shop.module.activity.service;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.CommonResult;
import com.shop.module.activity.entity.ActivityInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityInfoService {

    CommonResult insert(ActivityInfo activityInfo);

    CommonResult selectByNameVague(int current, int size, String actvityName);

    ActivityInfo selectByName(String actvityName);

    Integer selectCount();

    CommonResult modifyStatus(String activityStatus, String activityId);

    CommonResult deleteActivity(String activityId);

    CommonResult updateNum(int num, String activityId);

    ActivityInfo selectActivityByName(String activityName);

    List<String> selectName();

    String selectReturnImg();

    List<ActivityInfo> selectActivityNotOne();

    ActivityInfo selectById(String activityId);

    List<JSONObject> selectFourImg();

    List<ActivityInfo> selectTime();

    int modifyAllItemStatus(String isActivity,String activityId);

    int modifyItemStatus(String isActivity,String itemId);
}
