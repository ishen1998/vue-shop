package com.shop.module.activity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.CommonResult;
import com.shop.module.activity.config.ItemCount;
import com.shop.module.activity.dao.ActivityInfoDao;
import com.shop.module.activity.dao.ActivityItemInfoMapper;
import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.activity.service.ActivityInfoService;
import com.shop.module.activity.service.ActivityItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class ActivityInfoServiceImp implements ActivityInfoService {
    @Autowired
    private ActivityInfoDao activityInfoDao;
    @Autowired
    private ActivityItemInfoService activityInfoService;
    @Autowired
    private ActivityItemInfoMapper activityItemInfoMapper;
    @Autowired
    private ItemCount itemCount;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult insert(ActivityInfo activityInfo) {
        int row = activityInfoDao.insert(activityInfo);
        return itemCount.judge2(row);
    }

    @Override
    public ActivityInfo selectByName(String actvityName) {
        ActivityInfo activityInfo = activityInfoDao.selectByName(actvityName);
        return activityInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult selectByNameVague(int current,int size,String activityName) {
        List<ActivityInfo> activityInfos = activityItemInfoMapper.selectByNameVague(new Page(current,size),activityName).getRecords();
        int count = activityItemInfoMapper.selectByNameVagueCount(activityName);
        Map<String,Object> activityAndCount =  itemCount.getMap(activityInfos,count);
        return itemCount.judge(activityAndCount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer selectCount() {
        return activityInfoDao.selectCount();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult modifyStatus(String activityStatus, String activityId) {
        int rows=0;
        if(activityStatus.equalsIgnoreCase("Y")) {
            ActivityInfo activityInfo = activityInfoDao.selectById(activityId);
            int [] judges = itemCount.compareToTime(activityInfo);
            int judge = judges[0];
            int judge1 = judges[1];
            if ((judge == 0 || judge < 0) && (judge1 > 0 || judge1 == 0)) {
               rows= activityInfoDao.modifyStatus("Y",activityId);
            }else{
                return CommonResult.failed("还未到开启时间，或开启时间以过，请修改时间",null);
            }
        }else{
            rows = activityInfoDao.modifyStatus(activityStatus, activityId);
        }
        modifyAllItemStatus(activityStatus,activityId);
           return itemCount.judge2(rows);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult deleteActivity(String activityId) {
        int row=0;
        String status = activityInfoDao.selectStatus(activityId);
        if(!status.equalsIgnoreCase("Y")){
             row = activityInfoDao.deleteActivity(activityId);
             activityInfoService.deleteAll(activityId);
             modifyAllItemStatus("N",activityId);
            return itemCount.judge2(row);
        }
        return itemCount.judge2(row);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult updateNum(int num, String activityId) {
        int row = activityInfoDao.updateNum(num, activityId);
        return itemCount.judge2(row);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ActivityInfo selectActivityByName(String activityName) {
        return activityInfoDao.selectActivityByName(activityName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> selectName() {
        return activityInfoDao.selectName();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String selectReturnImg() {
        return activityInfoDao.selectReturnImg();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ActivityInfo> selectActivityNotOne() {
        return activityInfoDao.selectActivityNotOne();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ActivityInfo selectById(String activityId) {
            return activityInfoDao.selectById(activityId);
    }

    @Override
    public List<JSONObject> selectFourImg() {
        return activityInfoDao.selectFourImg();
    }

    @Override
    public List<ActivityInfo> selectTime() {
        return activityInfoDao.selectTime();
    }

    @Override
    public int modifyAllItemStatus(String isActivity, String activityId) {
        return activityInfoDao.modifyAllItemStatus(isActivity,activityId);
    }

    @Override
    public int modifyItemStatus(String isActivity, String itemId) {
        return activityInfoDao.modifyItemStatus(isActivity,itemId);
    }


}
