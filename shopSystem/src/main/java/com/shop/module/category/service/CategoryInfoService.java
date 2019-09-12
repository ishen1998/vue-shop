package com.shop.module.category.service;

import com.alibaba.fastjson.JSONObject;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.category.entity.CategoryInfo;
import com.shop.module.shopmessage.entity.ItemInfo;

import java.util.List;

public interface CategoryInfoService {
    /**
     * 新增
     * @param categoryInfo
     */
    void insert(CategoryInfo categoryInfo) throws CustomizeExp;

    /**
     * 修改
     * @param categoryInfo
     */
    void update(CategoryInfo categoryInfo) throws CustomizeExp;

    /**
     * 根据id删除
     * @param categoryId
     */
    void deleteById(String categoryId) throws CustomizeExp;

    /**
     * 查询所有并返回列表
     * @return
     */
    List<CategoryInfo> queryAll();
    /**
     * 根据Id查询
     * @param categoryId
     */
    CategoryInfo selectById(String categoryId) throws CustomizeExp;

    /**
     * 根据分类名模糊查询
     * @param categoryName
     */
    List<CategoryInfo> selectCategoryName(String categoryName) throws CustomizeExp;
    /**
     * 根据分类名查询
     * @param categoryName
     */
    CategoryInfo selectByName(String categoryName) throws CustomizeExp;
    /**
     * 根据Id修改状态
     * @param categoryInfo
     */
    void updateStatus(CategoryInfo categoryInfo) throws CustomizeExp;

    /**
     * 根据分类id查询
     * @param categoryId
     */
    CategoryInfo SelectItemById(String categoryId) throws CustomizeExp;

    /**
     * 查询数据条数
     */
    int selectCount();

    /**
     * 根据列表名称查询
     * @param listParent
     */
    List<CategoryInfo> selectByParent(String listParent) throws CustomizeExp;
    /**
     * 查询父分类名称
     */
    List<String> selectAllCategory();

    /**
     * 根据父分类名称查询所有商品
     */
    List<ItemInfo> selectAllParentItem(String listParent);

    /**
     * 根据父分类名称查询所有商品
     */
    List<JSONObject> selectCategoryNameId();

    /**
     * 查询6分类ID
     * @return
     */
    List<String> selectSixId();
}
