package com.shop.module.category.service.impl;

import com.shop.module.category.dao.CategoryLogDao;
import com.shop.module.category.entity.CategoryLog;
import com.shop.module.category.service.CategoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryLogServiceImpl implements CategoryLogService {
    @Autowired
    private CategoryLogDao categoryLogDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertItemId(CategoryLog categoryLog) {
        categoryLogDao.insertItemId(categoryLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(String categoryId) {
        categoryLogDao.deleteAll(categoryId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> queryAllItemId(String categoryId) {
        return categoryLogDao.queryAllItemId(categoryId);
    }
}
