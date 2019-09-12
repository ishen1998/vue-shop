package com.shop.module.shopmessage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * item_info
 * @author 
 */
@Data
@TableName("item_info")
public class ItemInfo implements Serializable {
    @TableId
    private String itemId;
    @NotBlank(message = "商品名称不能为空")
    private String itemName;

    private String categoryId;

    /**
     * 商品大分类
     */
    private String listParent;

    @Min(value = 0,message = "商品数量不能为负数")
    private Integer num;

    @Min(value = 0,message = "商品价格不能为负数")
    private BigDecimal price;

    private String locality;

    @NotBlank(message = "封面图不能为空")
    private String coverImg;

    @NotBlank(message = "轮播图片不能为空")
    private String returnImg;
    @NotBlank(message = "商品详情不能为空")
    private String itemDetail;

    @ApiModelProperty(value = "上架状态：N->下架；Y->上架")
    @NotBlank(message = "上架状态只能为(Y/N)")
    private String isShow;

    @ApiModelProperty(value = "活动状态：N->未参与活动；Y->参与活动中")
    private String isActivity;

    private String isPublicity;

    private String parameterName;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private Integer revision;

    private String activityPrice;

    private String localityId;

    private List<PriceAndStatus> priceAndStatuses;

    private static final long serialVersionUID = 1L;

}