package com.shop.module.category.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * category_log
 * @author 
 */
public class CategoryLog implements Serializable {


    /**
     * 商品id
     */
    private String itemId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 分类id
     */
    private String categoryId;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryLog that = (CategoryLog) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, categoryId);
    }


    @Override
    public String toString() {
        return "CategoryLog{" +
                "itemId='" + itemId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}