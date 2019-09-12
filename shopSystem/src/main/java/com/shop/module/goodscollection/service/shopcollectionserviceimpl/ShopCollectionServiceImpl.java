package com.shop.module.goodscollection.service.shopcollectionserviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shop.module.admin.util.SpringSecurityUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.goodscollection.dao.ShopCollectionMapper;
import com.shop.module.goodscollection.entity.BatchDeleteCollection;
import com.shop.module.goodscollection.entity.ShopCollection;
import com.shop.module.goodscollection.service.ShopCollectionService;

import com.shop.module.shopmessage.dao.ItemInfoDao;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YeLei
 */
@Service
public class ShopCollectionServiceImpl implements ShopCollectionService {

    @Autowired
    private ShopCollectionMapper shopCollectionMapper;
    @Autowired
    private SpringSecurityUtil securityUtil;
    @Autowired
    private ItemInfoDao itemInfoDao;

    private final static String Y = "Y";
    private final static String N = "N";

    /**
     * 删除单件收藏的商品
     * @param cId
     * @param itemId
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String cId,String itemId) throws CustomizeExp {
        QueryWrapper<ShopCollection> queryWrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap<>(2);
        map.put("c_id", cId);
        map.put("item_id", itemId);
        queryWrapper.allEq(map);
        int delete = shopCollectionMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("删除失败,该商品不存在");
        }
        return delete;
    }

    /**
     * 批量删除收藏的商品
     * @param batchDeleteCollection
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelete(BatchDeleteCollection batchDeleteCollection) throws CustomizeExp {
        QueryWrapper<ShopCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true,"c_id",batchDeleteCollection.getCId());
        queryWrapper.in("id",batchDeleteCollection.getId());
        String join = String.join(",", batchDeleteCollection.getId());
        int delete = shopCollectionMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("删除失败");
        }
        return delete;
    }

    /**
     * 添加商品到收藏夹
     * @param record
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(ShopCollection record) throws CustomizeExp {
        ItemInfo itemInfo = itemInfoDao.selectByPrimaryKey(record.getItemId());
        if (itemInfo==null){
            throw new CustomizeExp("该商品已不存在，无法加入收藏夹");
        }
        if (N.equals(itemInfo.getIsShow())){
            throw new CustomizeExp("该商品已下架，无法加入收藏夹");
        }
        record.setCollectionBy(securityUtil.currentUserName());
        record.setCollectionTime(new Date());
        QueryWrapper<ShopCollection> queryWrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap<>(2);
        map.put("c_id",record.getCId());
        map.put("item_id",record.getItemId());
        queryWrapper.allEq(map);
        ShopCollection shopCollection = shopCollectionMapper.selectOne(queryWrapper);
        if (shopCollection != null){
            throw new CustomizeExp("商品已存在");
        }
        int insert = shopCollectionMapper.insert(record);
        if (insert == 0){
            throw new CustomizeExp("收藏失败");
        }
        return insert;
    }

    /**
     * 根据客户ID清空收藏商品
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int cleanAllCollection(String cId) throws CustomizeExp {
        QueryWrapper<ShopCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "c_id", cId);
        int delete = shopCollectionMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("购物车清空失败");
        }
        return delete;
    }

    /**
     * 根据客户ID查询收藏商品
     * @param cId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ShopCollection> selectCollectionShop(String cId) {
        QueryWrapper<ShopCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true,"c_id",cId);
        return shopCollectionMapper.selectList(queryWrapper);
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public List<ShopCollection> selectCollectionShop(String cId) {
//        List<ShopCollection> shopCollections = selectShopMapper.selectCollectionShop(cId);
//        return shopCollections;
//    }
}
