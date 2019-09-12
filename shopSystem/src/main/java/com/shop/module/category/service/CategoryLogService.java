package com.shop.module.category.service;

import com.shop.module.category.entity.CategoryLog;

import java.util.List;

public interface CategoryLogService {
    /**
     * 新增
     * @param categoryLog
     */
    void insertItemId(CategoryLog categoryLog);
    /**
     * 清空一个分类
     */
    void deleteAll(String categoryId);
    /**
     * 查询所有并返回列表
     * @return
     */
    List<String> queryAllItemId(String categoryId);
}
