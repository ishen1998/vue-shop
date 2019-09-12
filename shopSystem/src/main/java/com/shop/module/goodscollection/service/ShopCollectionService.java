package com.shop.module.goodscollection.service;

import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.goodscollection.entity.BatchDeleteCollection;
import com.shop.module.goodscollection.entity.ShopCollection;

import java.util.List;


/**
 *  @author yelei
 */
public interface ShopCollectionService {

    int deleteByPrimaryKey(String cId,String itemId) throws CustomizeExp;

    int batchDelete(BatchDeleteCollection batchDeleteCollection) throws CustomizeExp;

    int insert(ShopCollection record) throws CustomizeExp;

    int cleanAllCollection(String cId) throws CustomizeExp;

    //List<ShopCollection> selectCollectionShop(String cId);

    List<ShopCollection> selectCollectionShop(String cId);
}
