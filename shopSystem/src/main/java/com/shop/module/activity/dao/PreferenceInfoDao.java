package com.shop.module.activity.dao;

import com.shop.module.activity.entity.PreferenceInfo;
import com.shop.module.activity.entity.TypeAndDis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PreferenceInfoDao {
    List<PreferenceInfo> selectInfo();
    TypeAndDis selectInfoById(String preferenceItemId);
}
