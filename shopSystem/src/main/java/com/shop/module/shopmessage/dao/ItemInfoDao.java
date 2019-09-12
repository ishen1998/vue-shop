package com.shop.module.shopmessage.dao;

import com.shop.module.shopmessage.entity.ItemInfo;
import com.shop.module.shopmessage.entity.ItemStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YeLei
 */
@Mapper
public interface ItemInfoDao {

    /**
     * 根据主键删除商品ID
     * @param itemId
     * @return
     */
    int deleteByPrimaryKey(String itemId);

    /**
     * 增添商品
     * @param record
     * @return
     */
    int insert(ItemInfo record);

    //int insertSelective(ItemInfo record);

    /**
     * 根据ID查询商品
     * @param itemId
     * @return
     */
    ItemInfo selectByPrimaryKey(String itemId);

    /**
     * 选择字段更新商品
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ItemInfo record);

    /**
     * 更新商品
     * @param record
     * @return
     */
    int updateByPrimaryKey(ItemInfo record);

    /**
     * 查询所有商品
     * @return
     */
    List<ItemInfo> findAllShopInfo();

    /**
     * 上下架商品
     * @param itemId
     * @param isShow
     * @return
     */
    int updatePublishStatus(String itemId,@Param("isShow") String isShow);

    /**
     * 新增商品
     * @param itemInfo
     * @return
     */
    void addNewShop(ItemInfo itemInfo);

    /**
     * 新增商品存入图片的接口
     * @param itemId
     * @return
     */
    List<String> shopReturnImg(String itemId);

    /**
     * 查询商品名称
     * @param itemName
     * @return
     */
    List<ItemInfo> getShopName(@Param("itemName") String itemName);

    /**
     * 查询商品生产地
     * @param locality
     * @return
     */
    List<ItemInfo> getShopLocality(@Param("locality") String locality,@Param("itemName")String itemName);

    /**
     * 联合商品订单查询
     * @param itemId
     * @return
     */
    ItemStatus getOrderStatus(String itemId);

    /**
     * 更新商品上下架（纯净版）
     * @param itemId
     * @param isShow
     * @return
     */
    int updateShopStatus(@Param("itemId") String itemId,@Param("isShow") String isShow);

}
