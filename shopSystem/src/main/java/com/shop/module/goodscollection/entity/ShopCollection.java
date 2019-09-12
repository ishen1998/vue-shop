package com.shop.module.goodscollection.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shop.module.shopmessage.entity.ItemInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * shop_collection
 * @author YeLei
 */
@Data
@TableName("shop_collection")
public class ShopCollection implements Serializable {

    /**
     * 收藏夹ID
     */
    @TableId("id")
    private String id;

    /**
     * 商品id
     */
    @NotBlank(message = "商品ID不能为空")
    private String itemId;

    /**
     * 收藏人
     */
    private String collectionBy;

    /**
     * 收藏时间
     */
    private Date collectionTime;

    /**
     * 客户ID
     */
    @NotBlank(message = "客户ID不能为空")
    private String cId;

    /**
     * 商品图片
     */
    private String itemImg;

    /**
     * 备用字段
     */
    private String standby3;

    /**
     * 备用字段
     */
    private String standby4;

//    private ItemInfo itemInfo;

    private static final long serialVersionUID = 1L;

}