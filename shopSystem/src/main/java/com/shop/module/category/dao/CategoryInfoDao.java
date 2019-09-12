package com.shop.module.category.dao;

import com.alibaba.fastjson.JSONObject;
import com.shop.module.category.entity.CategoryInfo;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CategoryInfoDao继承基类
 */
@Mapper
public interface CategoryInfoDao {
    /**
     * 新增
     * @param categoryInfo
     */
    void insert(CategoryInfo categoryInfo);

    /**
     * 修改
     * @param categoryInfo
     */
    void update(CategoryInfo categoryInfo);

    /**
     * 根据id删除
     * @param categoryId
     */
    void deleteById(String categoryId);

    /**
     * 查询所有并返回列表
     * @return
     */
    List<CategoryInfo> queryAll();
    /**
     * 根据Id查询
     * @param categoryId
     */
    CategoryInfo selectById(String categoryId);

    /**
     * 根据分类名模糊查询
     * @param categoryName
     */
    List<CategoryInfo> selectCategoryName(String categoryName);

    /**
     * 根据分类名查询
     * @param categoryName
     */
    CategoryInfo selectByName(String categoryName) ;
    /**
     * 根据Id修改状态
     * @param categoryInfo
     */
    void updateStatus(CategoryInfo categoryInfo);

    /**
     * 根据分类id查询
     * @param categoryId
     */
    CategoryInfo SelectItemById(String categoryId);

    /**
     * 查询数据条数
     */
    int selectCount();

    /**
     * 根据列表名称查询
     * @param listParent
     */
    List<CategoryInfo> selectByParent(String listParent);

    /**
     * 查询父分类名称
     */
    List<String> selectAllCategory();

    /**
     * 查询子分类名称及ID
     */
    List<JSONObject> selectCategoryNameId();
    /**
     * 根据父分类名称查询所有商品
     */
    List<ItemInfo> selectAllParentItem(String listParent);

    /**
     * 查询6分类ID
     * @return
     */
    List<String> selectSixId();

}