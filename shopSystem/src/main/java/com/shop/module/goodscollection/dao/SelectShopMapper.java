package com.shop.module.goodscollection.dao;

import com.shop.module.goodscollection.entity.ShopCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author YeLei
 */
@Mapper
public interface SelectShopMapper {

    List<ShopCollection> selectCollectionShop(String cId);

}
