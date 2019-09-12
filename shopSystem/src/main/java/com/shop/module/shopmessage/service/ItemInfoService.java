package com.shop.module.shopmessage.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.shopmessage.entity.ItemInfo;
import com.shop.module.shopmessage.entity.ItemStatus;
import com.shop.module.shopmessage.entity.LocalityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemInfoService {
    int deleteByPrimaryKey(String itemId) throws CustomizeExp;

    int insert(ItemInfo record) throws CustomizeExp;

    //int insertSelective(ItemInfo record) throws CustomizeExp;

    ItemInfo selectByPrimaryKey(String itemId) throws CustomizeExp;

    int updateByPrimaryKeySelective(ItemInfo record) throws CustomizeExp;

    int updateByPrimaryKey(ItemInfo record) throws CustomizeExp;

    List<ItemInfo> findAllShopInfo();

    int updatePublishStatus(String itemId,String isShow) throws CustomizeExp;

    ItemInfo addNewShop(ItemInfo itemInfo) throws CustomizeExp;

    IPage<ItemInfo> selectItemInfoList(Integer current,Integer size);

    List<String> shopReturnImg(String itemId);

    List<ItemInfo> getShopName(String itemName) throws CustomizeExp;

    IPage<ItemInfo> getShopCategoryName(Integer current, Integer size, String categoryId, String itemName) throws CustomizeExp;

    List<ItemInfo> getShopLocality(String locality,String itemName) throws CustomizeExp;

    ItemStatus getOrderStatus(String itemId);

    Map<Object,Object> selectItemInfoShop(Integer current, Integer size, String itemName, String categoryId) throws CustomizeExp;

    int updateShopStatus(String itemId,@Param("isShow") String isShow) throws CustomizeExp;

    List<String> selectLocality();
}
