package com.shop.module.activity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.CommonResult;
import com.shop.module.activity.config.ItemCount;
import com.shop.module.activity.dao.ActivityItemInfoDao;
import com.shop.module.activity.dao.ActivityItemInfoMapper;
import com.shop.module.activity.entity.*;
import com.shop.module.activity.service.ActivityInfoService;
import com.shop.module.activity.service.ActivityItemInfoService;
import com.shop.module.category.dao.CategoryInfoDao;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ActivityItemInfoServiceImp implements ActivityItemInfoService {

    @Autowired
    private ActivityItemInfoDao activityItemInfoDao;
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ActivityItemInfoMapper activityItemInfoMapper;
    @Autowired
    private CategoryInfoDao categoryInfoDao;
    @Autowired
    private ItemCount itemCount;

    @Override
    public CommonResult insert(ActivityItem activityItem) {
        return itemCount.judge2(activityItemInfoDao.insert(activityItem));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult select(String activityId) {
        List<ActivityInfo> activityInfos = activityItemInfoMapper.select(activityId);
        return itemCount.judge(activityInfos);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int selectNum(String activityId, String itemName) {
        return activityItemInfoDao.selectNum(activityId, itemName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ItemInfo> selectActivityItem(String activityId, String itemName, String msg, int current, int size) {
        List<ItemInfo> records = activityItemInfoMapper.selectActivityItemIpage(new Page(current, size), activityId, itemName, msg).getRecords();
        return records;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ItemInfo> selectAllItem(String msg, int current, int size) {
        List<ItemInfo> records = activityItemInfoMapper.selectAllItemIpage(new Page(current, size), msg).getRecords();
        return records;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ActivityInfo> selectActivity(int current, int size, String activityId) {
        List<ActivityInfo> records = activityItemInfoMapper.selectActivityIpage(new Page(current, size), activityId).getRecords();
        return records;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> selectHomeActivity() {
        Map<String, Object> map = new HashMap<>();
        String returnImg = activityInfoService.selectReturnImg();
        List<String> list = Arrays.asList(returnImg.split(","));
        List<String> six = categoryInfoDao.selectSixId();
        List<ImgAndId> idAndImg = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImgAndId imgAndId = new ImgAndId();
            imgAndId.setImg(list.get(i));
            imgAndId.setId(six.get(i));
            idAndImg.add(imgAndId);
        }
        map.put("returnImg", idAndImg);
        List<JSONObject> imgs = activityInfoService.selectFourImg();
        map.put("activityImg", imgs);
        List<String> name = activityInfoService.selectName();
        map.put("name", name);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> selectHomeActivityByName(String activityName) {
        Map<String, Object> map = new HashMap<>();
        List<HomeAllActivity> homeActivities = new ArrayList<>();
        List<List<ItemInfo>> lists = null;
        if (activityName.equals("全部")) {
            List<ActivityInfo> activityInfos = activityInfoService.selectActivityNotOne();
            for (int i = 0; i < activityInfos.size(); i++) {
                List<ItemInfo> itemInfos = selectActivityItem(activityInfos.get(i).getActivityId(), "", "", 1, 9);
                List<List<ItemInfo>> splitList = CommonResult.splitList(itemInfos, 3);
                homeActivities.add(new HomeAllActivity(activityInfos.get(i), splitList));
            }
        } else {
            ActivityInfo activityInfos = activityInfoService.selectActivityByName(activityName);
            List<ItemInfo> itemInfos = selectActivityItem(activityInfos.getActivityId(), "", "", 1, 9);
            lists = CommonResult.splitList(itemInfos, 3);
            List<HomeActivity> activities = new ArrayList<>();
            activities.add(new HomeActivity(activityInfos, lists));
            map.put("HomeActivity", activities);
            return map;
        }
        map.put("HomeActivity", homeActivities);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer selectCount() {
        return activityItemInfoDao.selectCount();
    }

    @Override
    public int deleteAll(String activityId) {
        return activityItemInfoDao.deleteAll(activityId);
    }

    @Override
    public CommonResult insertAll(ActivityVo activityVo) {
        ActivityInfo activityInfo = activityVo.getActivityInfo();
        List<ActivityItem> activityItems = activityVo.getActivityItem();
        String activityName = activityInfo.getActivityName();
        if (activityName.equals("")) {
            return CommonResult.failed("活动不存在", null);
        } else {
            if (activityInfoService.selectByName(activityName) == null) {
                if (activityInfo.getStartTime().compareTo(activityInfo.getEndTime()) > 0) {
                    return CommonResult.failed("开启时间与结束时间不合理", null);
                }
                int[] judges = itemCount.compareToTime(activityInfo);
                activityInfo = itemCount.changeData(activityInfo, judges);
                activityInfoService.insert(activityInfo);
                //获取新增活动信息
                ActivityInfo activityInfo1 = activityInfoService.selectByName(activityName);
                return itemCount.insertA(activityItems, activityInfo1);
            } else {
                return CommonResult.failed("该活动已存在！", null);
            }
        }
    }

    @Override
    public CommonResult modifyAll(ActivityVo activityVo) {
        if (activityVo == null) {
            return CommonResult.failed("活动不存在", null);
        }
        ActivityInfo activityInfo = activityVo.getActivityInfo();
        if (activityInfo.getStartTime().compareTo(activityInfo.getEndTime()) > 0) {
            return CommonResult.failed("开启时间与结束时间不合理", null);
        }
        int[] judges = itemCount.compareToTime(activityInfo);
        activityInfo = itemCount.changeData(activityInfo, judges);
        activityItemInfoDao.updateActivity(activityInfo);
        activityInfoService.modifyAllItemStatus("N", activityInfo.getActivityId());
        deleteAll(activityInfo.getActivityId());
        activityInfoService.updateNum(0, activityInfo.getActivityId());
        return itemCount.insertA(activityVo.getActivityItem(), activityInfo);
    }

    /**
     * 活动商品的模糊查询
     *
     * @param current
     * @param size
     * @param activityId
     * @param itemName
     * @param listParent
     * @return
     */
    @Override
    public Map<String, Object> selectSort(int current, int size, String activityId, String itemName, String listParent) {
        List<ItemInfo> itemInfos = activityItemInfoMapper.selectSort(new Page(current, size), activityId, itemName, listParent).getRecords();
        int count = selectSortCount(activityId, itemName, listParent);
        Map<String, Object> itemAndCount = itemCount.getMap(itemInfos, count);
        return itemAndCount;
    }

    @Override
    public int selectSortCount(String activityId, String itemName, String listParent) {
        return activityItemInfoMapper.selectSortCount(activityId, itemName, listParent);
    }

    /**
     * 商品的模糊查询
     *
     * @param current
     * @param size
     * @param itemName
     * @param listParent
     * @return
     */

    @Override
    public Map<String, Object> selectItemSort(int current, int size, String itemName, String listParent) {
        List<ItemInfo> itemInfos = activityItemInfoMapper.selectItemSort(new Page(current, size), itemName, listParent).getRecords();
        int count = selectItemSortCount(itemName, listParent);
        Map<String, Object> itemAndCount = itemCount.getMap(itemInfos, count);
        return itemAndCount;
    }

    @Override
    public int selectItemSortCount(String itemName, String listParent) {
        return activityItemInfoMapper.selectItemSortCount(itemName, listParent);
    }

    @Override
    public List<String> selectExists(String itemId) {
        return activityItemInfoDao.selectExists(itemId);
    }
}
