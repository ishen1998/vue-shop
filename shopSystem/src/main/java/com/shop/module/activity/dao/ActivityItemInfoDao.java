package com.shop.module.activity.dao;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.activity.entity.ActivityInfoAndItem;
import com.shop.module.activity.entity.ActivityItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityItemInfoDao {

    //添加商品到活动中
    int insert(ActivityItem activityItem);

    //根据id查看活动信息及处于活动中各个商品（为原型中查看和编辑中显示）
    List<ActivityInfo> select(String activityId);

    //更改活动信息，名称，图片，开始结束时间和状态
    int updateActivity(ActivityInfo activityInfo);

    //查看处于指定活动中的商品数量（以赋值给数据库中num，每添加一个商品加一）
    int selectNum(String activityId,String itemName);

    //商品数量
    Integer selectCount();

    int deleteAll(String activityId);

    List<String> selectExists(String itemId);

}
