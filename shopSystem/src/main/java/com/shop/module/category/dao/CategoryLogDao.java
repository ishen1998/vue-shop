package com.shop.module.category.dao;

import com.shop.module.category.entity.CategoryLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CategoryLogDao继承基类
 */
@Mapper
public interface CategoryLogDao  {
    /**
     * 新增
     * @param categoryLog
     */
    void insertItemId(CategoryLog categoryLog);
    /**
     * 清空表
     */
    void deleteAll(String categoryId);
    /**
     * 查询所有并返回列表
     * @return
     */
    List<String> queryAllItemId(String categoryId);
}