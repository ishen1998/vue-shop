package com.shop.module.category.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shop.module.shopmessage.entity.ItemInfo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * category_info
 * @author 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryInfo implements Serializable {
    /**
     * 分类Id
     */
    @NotBlank(message = "分类ID不能为空")
    private String categoryId;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类状态(Y:启用，N:禁用)
     */
    @NotBlank(message = "分类状态不能为空")
    private String categoryStatus;

    /**
     * 分类图片
     */
    private String categoryImage;
    /**
     * 父分类
     */
    private String listParent;

    public String getListParent() {
        return listParent;
    }

    public void setListParent(String listParent) {
        this.listParent = listParent;
    }

    /**
     * 商品实体类
     */
    private List<ItemInfo> itemInfo;

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public List<ItemInfo> getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(List<ItemInfo> itemInfo) {
        this.itemInfo = itemInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryInfo that = (CategoryInfo) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(revision, that.revision) &&
                Objects.equals(createBy, that.createBy) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateBy, that.updateBy) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(categoryStatus, that.categoryStatus) &&
                Objects.equals(categoryImage, that.categoryImage) &&
                Objects.equals(listParent, that.listParent) &&
                Objects.equals(itemInfo, that.itemInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, revision, createBy, createTime, updateBy, updateTime, categoryName, categoryStatus, categoryImage, listParent, itemInfo);
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "categoryId='" + categoryId + '\'' +
                ", revision=" + revision +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", categoryName='" + categoryName + '\'' +
                ", categoryStatus='" + categoryStatus + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                ", listParent='" + listParent + '\'' +
                ", itemInfo=" + itemInfo +
                '}';
    }
}