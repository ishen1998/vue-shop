package com.shop.module.shopcart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.module.activity.entity.ActivityItem;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;
import com.shop.module.shopcart.entity.TrolleyInfoListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author zhoulanzhen
 * @date 2019/8/001 20:05
 */
@Mapper
public interface TrolleyMapper extends BaseMapper<TrolleyInfoEntity> {
    List<TrolleyInfoListEntity> selectTrolleyInfoEntityList(String cId);

    ActivityItem selectTrolleyInfoEntity(String itemId);
}
