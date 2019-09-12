package com.shop.module.shopcart.service;

import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.shopcart.entity.BatchTrolley;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;
import com.shop.module.shopcart.entity.TrolleyInfoListEntity;

import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 20:04
 */
public interface TrolleyService {
    int insertTrolley(TrolleyInfoEntity trolleyInfoEntity) throws CustomizeExp;

    List<TrolleyInfoListEntity> selectTrolley(String cId);

    int deleteTrolley(String cId,String itemId) throws CustomizeExp;

    int deleteTrolleyId(String cId, String trolleyId) throws CustomizeExp;

    int batchDeleteItem(BatchTrolley batchTrolley) throws CustomizeExp;

    int batchDeleteTrolley(BatchTrolley batchTrolley) throws CustomizeExp;

    int emptyTrolley(String cId) throws CustomizeExp;
}
