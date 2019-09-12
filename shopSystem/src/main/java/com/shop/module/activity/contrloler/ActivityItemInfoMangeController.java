package com.shop.module.activity.contrloler;

import com.shop.common.CommonResult;
import com.shop.module.activity.config.ItemCount;
import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.activity.entity.ActivityItem;
import com.shop.module.activity.entity.ActivityVo;
import com.shop.module.activity.service.ActivityInfoService;
import com.shop.module.activity.service.ActivityItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RequestMapping("/activity")
@RestController
public class ActivityItemInfoMangeController {
    @Autowired
    private ActivityItemInfoService activityItemInfoService;
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ItemCount itemCount;

    /**
     *修改活动状态
     */
    @PreAuthorize("hasAuthority('Activity')")
    @GetMapping("/modifyStatus")
    public CommonResult modifyStatus(String activityStatus, String activityId) {
        return activityInfoService.modifyStatus(activityStatus, activityId);
    }

    /**
     *删除活动
     */
    @GetMapping("/deleteActivity")
    public CommonResult deleteActivity(String activityId) {
        return activityInfoService.deleteActivity(activityId);
    }

    /**
     * 点击编辑按钮，显示数据
     */
    @PreAuthorize("hasAuthority('Activity')")
    @GetMapping("/selectById")
    public CommonResult selectActivity(String activityId) {
            return activityItemInfoService.select(activityId);
    }

    /**
     * 插入活动，插入商品
     * @param activityVo
     * @return
     */
    @PreAuthorize("hasAuthority('Activity')")
    @RequestMapping("/insertAll")
    public CommonResult insertAll(@RequestBody ActivityVo activityVo) {
        return activityItemInfoService.insertAll(activityVo);
    }

    /**
     * 编辑活动
     * @param activityVo
     * @return
     */
    @PreAuthorize("hasAuthority('Activity')")
    @RequestMapping("/modifyAll")
    public CommonResult modifyAll(@RequestBody ActivityVo activityVo) {
        return activityItemInfoService.modifyAll(activityVo);
    }



    /**
     * 根据商品名模糊查询活动中的商品，且可以根据分类一起查询
     */
    @RequestMapping("/selectSort")
    public CommonResult selectSort(int current,int size,String activityId,String itemName, String listParent) {
        return CommonResult.success(activityItemInfoService.selectSort(current,size,activityId,itemName,listParent));
    }

    /**
     * 根据商品名模糊查询商品，且可以根据分类一起查询
     */
    @RequestMapping("/selectItemSort")
    public CommonResult selectItemSort(int current,int size,String itemName, String listParent) {
        return CommonResult.success(activityItemInfoService.selectItemSort(current,size,itemName,listParent));
    }

}




