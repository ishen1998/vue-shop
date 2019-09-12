package com.shop.module.activity.service;

import com.shop.common.CommonResult;
import com.shop.module.activity.entity.TypeAndDis;

public interface PreferenceInfoService {
    CommonResult selectInfo();
    TypeAndDis selectInfoById(String preferenceItemId);
}
