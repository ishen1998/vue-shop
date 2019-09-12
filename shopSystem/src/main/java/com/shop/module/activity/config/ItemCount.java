package com.shop.module.activity.config;

import com.shop.common.CommonResult;
import com.shop.module.activity.entity.*;
import com.shop.module.activity.service.ActivityInfoService;
import com.shop.module.activity.service.ActivityItemInfoService;
import com.shop.module.activity.service.PreferenceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ItemCount {

    @Autowired
    private ActivityItemInfoService activityItemInfoService;
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private PreferenceInfoService preferenceInfoService;


    public <T> Map<String, Object> getMap(T t, int count) {
        Map<String, Object> itemsAndCount = new HashMap<>();
        itemsAndCount.put("itemInfos", t);
        itemsAndCount.put("itemCount", count);
        return itemsAndCount;
    }

    public <T> CommonResult judge(T t) {
        if (t != null) {
            return CommonResult.success(t);
        } else {
            return CommonResult.failed("查询失败", null);
        }
    }

    public CommonResult judge2(int row) {
        if (row != 0) {
            return CommonResult.success("操作成功");
        } else {
            return CommonResult.failed("操作失败", null);
        }
    }

    /**
     * 插入活动数据
     *
     * @param activityItems
     * @param activityInfo
     * @return
     */
    public CommonResult insertA(List<ActivityItem> activityItems,ActivityInfo activityInfo) {
        int num = activityInfo.getNum();
        if(activityItems==null){
            return activityInfoService.updateNum(num, activityInfo.getActivityId());
        }
        for (int j = 0; j < activityItems.size(); j++) {
            num++;
            ActivityItem activityItem = activityItems.get(j);
            String preferenceItemId = activityInfo.getPreferenceItemId();
            TypeAndDis typeAndDis = preferenceInfoService.selectInfoById(preferenceItemId);
            Preference preference = PreferenceFactory.createPreference(typeAndDis.getPreferenceType(),activityItem.getPrice(), typeAndDis.getPreferenceDiscount());
            activityItem.setActivityPrice(preference.getActivityPrice());
            activityItem.setActivityId(activityInfo.getActivityId());
            activityItem.setActivityName(activityInfo.getActivityName());
            activityItem.setActivityImg(activityInfo.getActivityImg());
            activityItemInfoService.insert(activityItem);
            if(activityInfo.getActivityStatus().equalsIgnoreCase("Y")) {
                activityInfoService.modifyItemStatus("Y", activityItem.getItemId());
            }else{
                activityInfoService.modifyItemStatus("N", activityItem.getItemId());
            }
        }
        return activityInfoService.updateNum(num, activityInfo.getActivityId());
    }

    /**
     * 根据活动信息判断时间
     *
     * @param activityInfo
     * @return
     */
    public int[] compareToTime(ActivityInfo activityInfo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = activityInfo.getStartTime();
        String endTime = activityInfo.getEndTime();
        startTime = startTime.substring(0, 19);
        endTime = endTime.substring(0, 19);
        Date time = new Date();
        String nowTime = simpleDateFormat.format(time);
        //==0,开启时间等于现在的时间，可开启活动，》0，超过了当前的时间，用当前时间当开启时间，《0，大于现在时间，还不到开启时间
        int judge1 = startTime.compareTo(nowTime);
        //结束时间一定要大于开始时间，到达结束时间不能开启
        int judge2 = endTime.compareTo(nowTime);
        int[] judges = {judge1, judge2};
        return judges;
    }

    /**
     * 插入活动或编辑活动时，判断活动时间确定状态
     *
     * @param activityInfo
     * @param judges
     * @return
     */
    public ActivityInfo changeData(ActivityInfo activityInfo, int[] judges) {
        if (judges[0] > 0 || judges[1] < 0) {
            activityInfo.setActivityStatus("N");
        }
        return activityInfo;
    }

}
