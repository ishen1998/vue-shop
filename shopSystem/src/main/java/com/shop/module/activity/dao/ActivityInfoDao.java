package com.shop.module.activity.dao;

import com.alibaba.fastjson.JSONObject;
import com.shop.module.activity.entity.ActivityInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityInfoDao {
    //创建活动
    int insert(ActivityInfo activityInfo);

    //根据活动查询信息
    ActivityInfo selectByName(String activityName);

    //根据id查看活动信息
    ActivityInfo selectById(String activityId);

    //查看活动的记录数和上方一起用
    Integer selectCount();

    //原型中修改状态，（开启关闭活动）
    int modifyStatus(String activityStatus, String activityId);

    //原型中删除活动
    int deleteActivity(String activityId);

    //更新活动中num的值
    int updateNum(int num, String activityId);

    List<String> selectName();

    ActivityInfo selectActivityByName(String activityName);

    String selectReturnImg();

    List<JSONObject> selectFourImg();

    List<ActivityInfo> selectActivityNotOne();

    String selectStatus(String activityId);

    List<ActivityInfo> selectTime();

    int modifyAllItemStatus(String isActivity, String activityId);

    int modifyItemStatus(String isActivity, String itemId);
}
