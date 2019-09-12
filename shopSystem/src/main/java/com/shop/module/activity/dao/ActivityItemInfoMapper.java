package com.shop.module.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.activity.entity.ActivityInfo;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ActivityItemInfoMapper extends BaseMapper {
    //指定活动商品按排序
    IPage<ItemInfo> selectActivityItemIpage(Page page, String activityId, String itemName, String msg);

    //所有商品按排序
    IPage<ItemInfo> selectAllItemIpage(Page page, @Param("msg") String msg);

    //活动分页
    IPage<ActivityInfo> selectActivityIpage(Page page, String activityId);

    //根据id查看活动信息及处于活动中各个商品（为原型中查看和编辑中显示）
    List<ActivityInfo> select(String activityId);

    int selectActivityItemCount(String activityId);

    //活动商品模糊查询且可以按分类查询
    IPage<ItemInfo> selectSort(Page page, String activityId, String itemName, String listParent);

    int selectSortCount(String activityId, String itemName, String listParent);

    //商品模糊查询可按分类查询
    IPage<ItemInfo> selectItemSort(Page page, String itemName, String listParent);

    int selectItemSortCount(String itemName, String listParent);

    //原型中根据活动名称查看活动信息（模糊）
    IPage<ActivityInfo> selectByNameVague(Page page, String activityName);

    int selectByNameVagueCount(String activityName);
}
