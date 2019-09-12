package com.shop.module.activity.contrloler;

import com.shop.common.CommonResult;
import com.shop.module.activity.config.ItemCount;
import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.activity.service.ActivityInfoService;
import com.shop.module.activity.service.ActivityItemInfoService;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/activity")
@RestController
public class ActivityInfoController {
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ActivityItemInfoService activityItemInfoService;
    @Autowired
    ItemCount itemCount;

    /**
     *  显示首页活动数据
     */

    @GetMapping("/selectHomeActivityByName")
    public CommonResult selectHomeActivityByName(String activityName) {
        return CommonResult.success(activityItemInfoService.selectHomeActivityByName(activityName));
    }

    /**
     * 显示首页全部数据上半部分
     */

    @GetMapping("/selectHomeActivity")
    public CommonResult getHomeActivityInfo() {
        return CommonResult.success(activityItemInfoService.selectHomeActivity());
    }

    /**
     * 查找活动商品并排序
     * @param activityId
     * @param msg
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/selectActivity")
    public CommonResult selectById(String activityId,String itemName, String msg, int current, int size) {
        List<ItemInfo> itemInfos = activityItemInfoService.selectActivityItem(activityId,itemName, msg, current, size);
        int count = activityItemInfoService.selectNum(activityId,itemName);
        Map<String, Object> itemsAndCount = itemCount.getMap(itemInfos, count);
        return CommonResult.success(itemsAndCount);
    }

    /**
     * 查找所有商品并排序
     */

    @GetMapping("/selectAllItem")
    public CommonResult selectAllItem(String msg, int current, int size) {
        List<ItemInfo> itemInfos = activityItemInfoService.selectAllItem(msg, current, size);
        int count = activityItemInfoService.selectCount();
        Map<String, Object> itemsAndCount = itemCount.getMap(itemInfos, count);
        return CommonResult.success(itemsAndCount);
    }

    /**
     * 根据活动名查找活动
     */
    @GetMapping("/selectByName")
    public CommonResult selectInfo(int current,int size,String activityName) {
        return  activityInfoService.selectByNameVague(current,size,activityName);
    }

    /**
     *    前端点击活动管理按钮，初始先传值1进来
     */
    @GetMapping("/selectByPageTest")
    public CommonResult selectByPage(int current, int size) {
        List<ActivityInfo> activityInfos = activityItemInfoService.selectActivity(current, size, "1");
        int total = activityInfoService.selectCount();
        Map<String, Object> itemsAndCount = itemCount.getMap(activityInfos, total);
        return CommonResult.success(itemsAndCount);
    }
}
