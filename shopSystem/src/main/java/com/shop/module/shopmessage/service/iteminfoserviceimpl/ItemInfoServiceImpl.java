package com.shop.module.shopmessage.service.iteminfoserviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.activity.dao.ActivityItemInfoDao;
import com.shop.module.admin.util.SpringSecurityUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.order.dao.OrderDetailInfoDao;
import com.shop.module.order.dao.OrderInfoDao;
import com.shop.module.shopmessage.dao.ItemInfoDao;
import com.shop.module.shopmessage.dao.ItemInfoMapper;
import com.shop.module.shopmessage.dao.LocalityDao;
import com.shop.module.shopmessage.entity.ItemInfo;
import com.shop.module.shopmessage.entity.ItemStatus;
import com.shop.module.shopmessage.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author YeLei
 */
@Service
public class ItemInfoServiceImpl implements ItemInfoService {

    public static final String Y = "Y";

    @Autowired
    private ItemInfoDao itemInfoDao;
    @Autowired
    private OrderDetailInfoDao orderDetailInfoDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ItemInfoMapper itemInfoMapper;
    @Autowired
    private SpringSecurityUtil securityUtil;
    @Autowired
    private ActivityItemInfoDao activityItemInfoDao;
    @Autowired
    private LocalityDao localityDao;

    /**
     * 按商品主键删除商品
     * @param itemId
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String itemId) throws CustomizeExp {
        ItemStatus itemStatus = itemInfoDao.getOrderStatus(itemId);
        if (itemStatus == null) {
            throw new CustomizeExp("该商品不存在");
        }
        if (Y.equals(itemStatus.getIsShow())) {
            throw new CustomizeExp("该商品上架中无法删除");
        }
        String status = "0";
        if (status.equals(itemStatus.getOrderStatus())) {
            throw new CustomizeExp("该商品有订单正在进行无法删除");
        }
        int deleteByPrimaryKey = itemInfoDao.deleteByPrimaryKey(itemId);
        return deleteByPrimaryKey;
    }

    /**
     * 添加商品
     * @param record
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(ItemInfo record) throws CustomizeExp {
        record.setCreatedTime(new Date());
        record.setCreatedBy(record.getCreatedBy());
        int insert = itemInfoDao.insert(record);
        if (insert == 0){
            throw new CustomizeExp("添加失败");
        }
        return insert;
    }

    /**
     * 按商品主键查询商品
     * @param itemId
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemInfo selectByPrimaryKey(String itemId) throws CustomizeExp {
        ItemInfo itemInfo = itemInfoDao.selectByPrimaryKey(itemId);
        if (itemInfo == null){
            throw new CustomizeExp("数据库没有商品信息");
        }
        return itemInfo;
    }

    /**
     * 选择其中字段更新一个商品
     * @param record
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(ItemInfo record) throws CustomizeExp {
        record.setUpdatedBy(securityUtil.currentUserName());
        record.setUpdatedTime(new Date());
        int updateByPrimaryKeySelective = itemInfoDao.updateByPrimaryKeySelective(record);
        if (updateByPrimaryKeySelective == 0){
            throw new CustomizeExp("该更新商品不存在");
        }
        return updateByPrimaryKeySelective;
    }

    /**
     * 更新一个商品
     * @param record
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(ItemInfo record) throws CustomizeExp {
        //若商品中不存在当前商品
        if((itemInfoDao.selectByPrimaryKey(record.getItemId())) == null){
            throw new CustomizeExp("该更新商品不存在");
        }
        record.setUpdatedBy(securityUtil.currentUserName());
        record.setUpdatedTime(new Date());
        int updateByPrimaryKey = itemInfoDao.updateByPrimaryKey(record);
        return updateByPrimaryKey;
    }

    /**
     * 查询数据库中所有的商品
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ItemInfo> findAllShopInfo() {
        List<ItemInfo> allShopInfo = itemInfoDao.findAllShopInfo();
        return allShopInfo;
    }

    /**
     * 根据商品id更新商品状态
     * @param itemId
     * @param isShow
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePublishStatus(String itemId, String isShow) throws CustomizeExp {
        int i=0;
        //查询该商品正在进行中的订单数量
        List<String> list = activityItemInfoDao.selectExists(itemId);
        if(list.contains(Y)){
            throw new CustomizeExp("该商品处于活动中，暂不能修改");
        }else {
            i= itemInfoDao.updatePublishStatus(itemId, isShow);
        }
        int order = orderInfoDao.selectByItemId(itemId);
        if (order>0){
            throw new CustomizeExp("该商品有订单正在进行中");
        }else {
            //根据商品id修改订单商品的状态
            orderDetailInfoDao.updateByItemId(itemId, isShow);
        }
        return i;
    }

    /**
     * 新增一个商品
     * @param itemInfo
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemInfo addNewShop(ItemInfo itemInfo) throws CustomizeExp {
        itemInfo.setCreatedBy(securityUtil.currentUserName());
        itemInfo.setCreatedTime(new Date());
        ItemInfo itemInfo1;
        //若商品中不存在当前商品，执行添加操作
        if(null == (itemInfo1=itemInfoDao.selectByPrimaryKey(itemInfo.getItemId()))){
            itemInfoDao.addNewShop(itemInfo);
            return itemInfo;
        }
        //若数据库中的数据与传入的相同，不执行任何操作
        if(itemInfo.equals(itemInfo1)){
            return itemInfo;
        }
        //若数据库中的数据与传入的不相同，插入新数据
        if(!(itemInfo.equals(itemInfo1))){
            itemInfoDao.addNewShop(itemInfo);
        }
        return itemInfo;
    }

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public IPage<ItemInfo> selectItemInfoList(Integer current, Integer size) {
        IPage<ItemInfo> itemInfoIPage = itemInfoMapper.selectItemInfoList(new Page(current, size));
        return itemInfoIPage;
    }

    /**
     * 轮播图以集合的方式存入
     * @param itemId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> shopReturnImg(String itemId) {
        ItemInfo itemInfo = itemInfoDao.selectByPrimaryKey(itemId);
        String returnImg = itemInfo.getReturnImg();
        List<String> list = Arrays.asList(returnImg.split(","));
        return list;
    }

    /**
     * 根据商品名称查询
     * @param itemName
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ItemInfo> getShopName(String itemName) throws CustomizeExp {
        List<ItemInfo> shopName = itemInfoDao.getShopName(itemName);
        if (null == shopName || shopName.size()==0){
            throw new CustomizeExp("商品名不存在");
        }
        return shopName;
    }

    /**
     * 按商品分类ID和商品名称查询
     * @param categoryId
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public IPage<ItemInfo> getShopCategoryName(Integer current,Integer size,String categoryId,String itemName) throws CustomizeExp {
        IPage<ItemInfo> shopCategoryName = itemInfoMapper.getShopCategoryName(new Page(current, size), categoryId, itemName);
        return shopCategoryName;
    }

    /**
     * 按商品产地和商品名称查询
     * @param locality
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ItemInfo> getShopLocality(String locality,String itemName) throws CustomizeExp {
        if (locality == null || locality == ""){
            throw new CustomizeExp("该商品产地不存在");
        }
        List<ItemInfo> shopLocality = itemInfoDao.getShopLocality(locality,itemName);
        if (shopLocality.size()==0){
            throw new CustomizeExp("该商品不存在此产地或者此产地不存在此商品");
        }
        return shopLocality;
    }

    /**
     * 联表查询订单表存在的数据:
     *  商品是否有正在进行中的状态
     * @param itemId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemStatus getOrderStatus(String itemId) {
        return itemInfoDao.getOrderStatus(itemId);
    }

    /**
     * 商品名称和商品分类ID分页查询
     * @param current
     * @param size
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<Object,Object> selectItemInfoShop(Integer current, Integer size, String itemName, String categoryId) throws CustomizeExp {
        IPage<JSONObject> itemInfoIPage = itemInfoMapper.selectItemInfoShop(new Page(current, size),itemName,categoryId);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("itemShop",itemInfoIPage);
        return map;
    }

    /**
     * 更新商品状态(纯净版)
     * @param itemId
     * @param isShow
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateShopStatus(String itemId, String isShow) throws CustomizeExp {
        int i = itemInfoDao.updateShopStatus(itemId, isShow);
        if(i == 0){
            throw new CustomizeExp("商品修改失败");
        }
        return i;
    }

    /**
     * 产地
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> selectLocality() {
        List<String> list = localityDao.selectLocality();
        return list;
    }

}
