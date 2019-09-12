package com.shop.module.shopcart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 16:59
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrolleyInfoListEntity {
    /**
     * 购物车id
     */
    @TableId
    private String trolleyId;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 客户id
     */
    @NotBlank(message = "客户ID不能为空")
    private String cId;

    /**
     * 客户登录名称
     */
    @NotBlank(message = "客户登入名不能为空")
    private String cLoginName;

    /**
     * 商品id
     */
    @NotBlank(message = "商品ID不能为空")
    private String itemId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称名称不能为空")
    private String itemName;

    /**
     * 购买数量
     */
    @Min(value = 0,message = "购买数量不能为负数")
    private Integer num;

    /**
     * 商品金额
     */
    @Min(value = 0,message = "商品价格不能为负数")
    private BigDecimal price;

    /**
     * 活动金额
     */
    @Min(value = 0,message = "活动价格不能为负数")
    private BigDecimal activityPrice;

    private String itemImg;

    private String isActivity;

    /**
     * 商品上下架
     */
//    @NotNull(message = "商品上下架状态不能为空")
    private String isShow;
}
