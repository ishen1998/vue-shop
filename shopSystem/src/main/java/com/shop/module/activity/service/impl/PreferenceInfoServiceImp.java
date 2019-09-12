package com.shop.module.activity.service.impl;

import com.shop.common.CommonResult;
import com.shop.module.activity.config.ItemCount;
import com.shop.module.activity.dao.PreferenceInfoDao;
import com.shop.module.activity.entity.PreferenceInfo;
import com.shop.module.activity.entity.TypeAndDis;
import com.shop.module.activity.service.PreferenceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PreferenceInfoServiceImp implements PreferenceInfoService {
    @Autowired
    private PreferenceInfoDao preferenceInfoDao;
    @Autowired
    private ItemCount itemCount;

    @Override
    public CommonResult selectInfo() {
        List<PreferenceInfo> preferenceInfos = preferenceInfoDao.selectInfo();
        return itemCount.judge(preferenceInfos);
    }

    @Override
    public TypeAndDis selectInfoById(String preferenceItemId) {
        return preferenceInfoDao.selectInfoById(preferenceItemId);
    }
}
