package com.shop.module.shopcart.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shop.module.activity.entity.ActivityItem;
import com.shop.module.admin.util.SpringSecurityUtil;
import com.shop.module.admin.util.StaticUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.shopcart.dao.TrolleyMapper;
import com.shop.module.shopcart.entity.BatchTrolley;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;
import com.shop.module.shopcart.entity.TrolleyInfoListEntity;
import com.shop.module.shopcart.service.TrolleyService;
import com.shop.module.shopmessage.dao.ItemInfoDao;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 20:05
 */
@Service
public class TrolleyServiceImpl implements TrolleyService {
    @Autowired
    private TrolleyMapper trolleyMapper;
    @Autowired
    private SpringSecurityUtil securityUtil;
    @Autowired
    private ItemInfoDao itemInfoDao;
    private final static String status = "Y";

    /**
     * 添加商品到购物车
     * @param trolleyInfoEntity
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertTrolley(TrolleyInfoEntity trolleyInfoEntity) throws CustomizeExp {
        ItemInfo itemInfo = itemInfoDao.selectByPrimaryKey(trolleyInfoEntity.getItemId());
        if (itemInfo == null) {
            throw new CustomizeExp("加入购物车的商品不存在");
        }
        if (!status.equals(itemInfo.getIsActivity())){
            throw new CustomizeExp("该商品已关闭该活动");
        }
        if (!StaticUtil.STATUS.equals(itemInfo.getIsShow())){
            throw new CustomizeExp("该商品已下架");
        }
        if ((itemInfo.getNum()-trolleyInfoEntity.getNum())<0){
            throw new CustomizeExp("该商品库存不足");
        }
        trolleyInfoEntity.setCreatedBy(securityUtil.currentUserName());
        trolleyInfoEntity.setCreatedTime(new Date());
        QueryWrapper<TrolleyInfoEntity> queryWrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap<>(2);
        map.put("c_id", trolleyInfoEntity.getCId());
        map.put("item_id", trolleyInfoEntity.getItemId());
        queryWrapper.allEq(map);
        TrolleyInfoEntity trolley = trolleyMapper.selectOne(queryWrapper);
        //如果购物车中有该商品把数量相加
        if (trolley != null) {
            Integer i = trolleyInfoEntity.getNum() + trolley.getNum();
            trolleyInfoEntity.setNum(i);
            trolleyInfoEntity.setUpdatedBy(trolleyInfoEntity.getCLoginName());
            trolleyInfoEntity.setUpdatedTime(new Date());
            trolleyMapper.update(trolleyInfoEntity,queryWrapper);
            return i;
        }
        int insert = trolleyMapper.insert(trolleyInfoEntity);
        if (insert==0){
            throw new CustomizeExp("添加购物车失败");
        }
        return insert;
    }

    /**
     * 根据用户ID查找购物车商品
     * @param cId
     * @return
     */
    @Override
    public List<TrolleyInfoListEntity> selectTrolley(String cId) {
        List<TrolleyInfoListEntity> trolleyInfoListEntities = trolleyMapper.selectTrolleyInfoEntityList(cId);
//        for (TrolleyInfoListEntity entity : trolleyInfoListEntities) {
//            if (status.equals(entity.getIsActivity())) {
//                entity.setPrice(entity.getActivityPrice().multiply(new BigDecimal(entity.getNum())));
//            }else {
//                entity.setPrice(entity.getPrice().multiply(new BigDecimal(entity.getNum())));
//            }
//        }
        return trolleyInfoListEntities;
    }

    /**
     * 删除一件商品
     * @param cId
     * @param itemId
     * @return
     * @throws CustomizeExp
     */
    @Override
    public int deleteTrolley(String cId, String itemId) throws CustomizeExp {
        QueryWrapper<TrolleyInfoEntity> queryWrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap<>(2);
        map.put("c_id", cId);
        map.put("item_id", itemId);
        queryWrapper.allEq(map);
        int delete = trolleyMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("删除失败");
        }
        return delete;
    }


    /**
     * 删除一个购物车
     * @param cId
     * @param trolleyId
     * @return
     * @throws CustomizeExp
     */
    @Override
    public int deleteTrolleyId(String cId, String trolleyId) throws CustomizeExp {
        QueryWrapper<TrolleyInfoEntity> queryWrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap<>(2);
        map.put("c_id", cId);
        map.put("trolley_id", trolleyId);
        queryWrapper.allEq(map);
        int delete = trolleyMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("购物车删除失败");
        }
        return delete;
    }




    /**
     * 批量删除购物中的商品
     * @param batchTrolley
     * @return
     * @throws CustomizeExp
     */
    @Override
    public int batchDeleteItem(BatchTrolley batchTrolley) throws CustomizeExp {
        QueryWrapper<TrolleyInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "c_id", batchTrolley.getCId());
        queryWrapper.in("item_id",batchTrolley.getItemId());
        String join = String.join(",", batchTrolley.getItemId());
        int delete = trolleyMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("删除失败");
        }
        return delete;
    }


    /**
     * 批量删除购物中的商品
     * @param batchTrolley
     * @return
     * @throws CustomizeExp
     */
    @Override
    public int batchDeleteTrolley(BatchTrolley batchTrolley) throws CustomizeExp {
        QueryWrapper<TrolleyInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "c_id", batchTrolley.getCId());
        queryWrapper.in("trolley_id",batchTrolley.getItemId());
        String join = String.join(",", batchTrolley.getItemId());
        int delete = trolleyMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("删除失败");
        }
        return delete;
    }



    /**
     * 清空购物车
     * @return
     * @throws CustomizeExp
     */
    @Override
    public int emptyTrolley(String cId) throws CustomizeExp {
        QueryWrapper<TrolleyInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "c_id", cId);
        int delete = trolleyMapper.delete(queryWrapper);
        if (delete==0){
            throw new CustomizeExp("购物车清空失败");
        }
        return delete;
    }


}
