package com.shop.module.activity.config;

import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.activity.service.ActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TimeThread {

    @Autowired
    private ActivityInfoService activityInfoService;

    /**
     * 一分钟判断一次活动时间
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ActivityInfo> activityInfos = activityInfoService.selectTime();
        List<String> activityIds = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();
        Date time = new Date();
        String nowTime = simpleDateFormat.format(time);
        for (int i = 0; i < activityInfos.size(); i++) {
            activityIds.add(activityInfos.get(i).getActivityId());
            endTimes.add(activityInfos.get(i).getEndTime());
        }
        for (int j = 0; j < endTimes.size(); j++) {
            String endTime = endTimes.get(j).substring(0, 19);
            if (endTime.compareTo(nowTime) < 0) {
                activityInfoService.modifyStatus("N", activityIds.get(j));
                activityInfoService.modifyAllItemStatus("N",activityIds.get(j));
            }
        }
    }
}




