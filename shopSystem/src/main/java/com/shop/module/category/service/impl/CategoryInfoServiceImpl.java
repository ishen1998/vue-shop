package com.shop.module.category.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.category.dao.CategoryInfoDao;
import com.shop.module.category.entity.CategoryInfo;
import com.shop.module.category.entity.CategoryLog;
import com.shop.module.category.service.CategoryInfoService;
import com.shop.module.category.service.CategoryLogService;
import com.shop.module.shopmessage.entity.ItemInfo;
import com.shop.module.shopmessage.entity.ItemStatus;
import com.shop.module.shopmessage.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryInfoServiceImpl implements CategoryInfoService {
    @Autowired
    private CategoryInfoDao categoryInfoDao;
    @Autowired
    private ItemInfoService itemInfoService;
    @Autowired
    private CategoryLogService categoryLogService;


    //新增
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(CategoryInfo categoryInfo) throws CustomizeExp {
        CategoryInfo categoryInfos=categoryInfoDao.selectByName(categoryInfo.getCategoryName());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        categoryInfo.setCategoryId(uuid);
        categoryInfo.setCreateTime(new Date());
        if (categoryInfos!=null) {
            throw  new CustomizeExp("此分类已存在");
        }
        categoryInfoDao.insert(categoryInfo);
    }

    //修改
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(CategoryInfo categoryInfo) throws CustomizeExp{
        CategoryLog categoryLog=new CategoryLog();
        CategoryInfo categoryInfos=categoryInfoDao.selectById(categoryInfo.getCategoryId());
        if (categoryInfos == null) {
            throw new CustomizeExp("分类Id不存在");
        }
        CategoryInfo categoryInfoss=categoryInfoDao.selectByName(categoryInfo.getCategoryName());
        if (categoryInfoss!=null&&!categoryInfoss.getCategoryId().equals(categoryInfos.getCategoryId())) {
            throw  new CustomizeExp("分类名已存在");
        }
        if((categoryInfo.getCategoryStatus()).equals("N")) {
            List<ItemInfo> itemInfos = SelectItemById(categoryInfo.getCategoryId()).getItemInfo();
            String status = "0";
            for (int i = 0; i < itemInfos.size(); i++) {
                ItemStatus itemStatus = itemInfoService.getOrderStatus(itemInfos.get(i).getItemId());
                if (status.equals(itemStatus.getOrderStatus())) {
                    throw new CustomizeExp("该分类有订单正在进行! 无法修改为禁用状态");
                }
                if ((itemInfos.get(i).getIsActivity()).equals("Y")) {
                    throw new CustomizeExp("该分类有商品正处于活动中! 无法修改为禁用状态");
                }
                if((itemInfos.get(i).getIsShow()).equals("Y")){
                    categoryLog.setCategoryId(categoryInfo.getCategoryId());
                    categoryLog.setItemId(itemInfos.get(i).getItemId());
                    categoryLogService.insertItemId(categoryLog);
                }
                itemInfoService.updateShopStatus(itemInfos.get(i).getItemId(), "N");
            }
            categoryInfo.setUpdateTime(new Date());
            categoryInfoDao.update(categoryInfo);
        }else {
            List<String> itemIds=categoryLogService.queryAllItemId(categoryInfo.getCategoryId());
            for (int i=0;i<itemIds.size();i++){
                itemInfoService.updateShopStatus(itemIds.get(i),"Y" );
            }
            categoryLogService.deleteAll(categoryInfo.getCategoryId());
            categoryInfo.setUpdateTime(new Date());
            categoryInfoDao.updateStatus(categoryInfo);
        }
    }

    //根据id删除
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String categoryId) throws CustomizeExp{
        CategoryInfo categoryInfos=categoryInfoDao.selectById(categoryId);
        if (categoryInfos == null) {
            throw  new CustomizeExp("分类Id不存在");
        }
        if(categoryInfos.getCategoryStatus().equals("Y")){
            throw  new CustomizeExp("分类处于启用中，无法删除！");
        }
        List<ItemInfo> itemInfos=SelectItemById(categoryId).getItemInfo();
        for (int i=0;i<itemInfos.size();i++){
            itemInfos.get(i).setCategoryId("262521157352161871");
            ItemInfo info=itemInfos.get(i);
            itemInfoService.updateByPrimaryKeySelective(info);
        }
        categoryInfoDao.deleteById(categoryId);
    }

    //查询所有并返回列表
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CategoryInfo> queryAll() {
        return categoryInfoDao.queryAll();
    }

    //根据Id查询
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryInfo selectById(String categoryId) throws CustomizeExp{
        CategoryInfo categoryInfos=categoryInfoDao.selectById(categoryId);
        return categoryInfoDao.selectById(categoryId);
    }

    //根据分类名模糊查询
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CategoryInfo> selectCategoryName(String categoryName) throws CustomizeExp{
        List<CategoryInfo> categoryInfos=categoryInfoDao.selectCategoryName("%"+categoryName+"%");
        if (categoryInfos == null ||categoryInfos.size()<=0) {
            throw new CustomizeExp("分类名不存在");
        }
        return categoryInfos;
    }

    //根据分类名查询
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryInfo selectByName(String categoryName) throws CustomizeExp{
        CategoryInfo categoryInfos=categoryInfoDao.selectByName(categoryName);
        if (categoryInfos == null ) {
            throw new CustomizeExp("分类名不存在");
        }
        return categoryInfoDao.selectByName(categoryName);
    }

    //根据Id修改状态
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(CategoryInfo categoryInfo) throws CustomizeExp{
        CategoryLog categoryLog=new CategoryLog();
        if((categoryInfo.getCategoryStatus()).equals("N")) {
            List<ItemInfo> itemInfos = SelectItemById(categoryInfo.getCategoryId()).getItemInfo();
            String status = "0";
            for (int i = 0; i < itemInfos.size(); i++) {
                ItemStatus itemStatus = itemInfoService.getOrderStatus(itemInfos.get(i).getItemId());
                if (status.equals(itemStatus.getOrderStatus())) {
                    throw new CustomizeExp("该分类有订单正在进行");
                }
                if ((itemInfos.get(i).getIsActivity()).equals("Y")) {
                    throw new CustomizeExp("该分类有商品正处于活动中");
                }
                if((itemInfos.get(i).getIsShow()).equals("Y")){
                    categoryLog.setCategoryId(categoryInfo.getCategoryId());
                    categoryLog.setItemId(itemInfos.get(i).getItemId());
                    categoryLogService.insertItemId(categoryLog);
                }
                itemInfoService.updateShopStatus(itemInfos.get(i).getItemId(), "N");
            }
            categoryInfo.setUpdateTime(new Date());
            categoryInfoDao.updateStatus(categoryInfo);
        }else {
            List<String> itemIds=categoryLogService.queryAllItemId(categoryInfo.getCategoryId());
            for (int i=0;i<itemIds.size();i++){
                itemInfoService.updateShopStatus(itemIds.get(i),"Y" );
            }
            categoryLogService.deleteAll(categoryInfo.getCategoryId());
            categoryInfo.setUpdateTime(new Date());
            categoryInfoDao.updateStatus(categoryInfo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryInfo SelectItemById(String categoryId) throws CustomizeExp{
        CategoryInfo categoryInfos=categoryInfoDao.SelectItemById(categoryId);
        if (categoryInfos == null ) {
            throw new CustomizeExp("分类Id不存在");
        }
        System.out.println(categoryInfos);
        return categoryInfoDao.SelectItemById(categoryId);
    }

    //查询数据条数
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int selectCount() {

        return categoryInfoDao.selectCount();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CategoryInfo> selectByParent(String listParent) throws CustomizeExp{
        List<CategoryInfo> categoryInfos=categoryInfoDao.selectByParent(listParent);
        if (categoryInfos == null || categoryInfos.size() == 0) {
            throw new CustomizeExp("父分类不存在");
        }
        return categoryInfoDao.selectByParent(listParent);
    }

    @Override
    public List<String> selectAllCategory() {
        return categoryInfoDao.selectAllCategory();
    }

    @Override
    public List<ItemInfo> selectAllParentItem(String listParent) {
        return categoryInfoDao.selectAllParentItem(listParent);
    }

    @Override
    public List<JSONObject> selectCategoryNameId (){
        return categoryInfoDao.selectCategoryNameId();
    }

    @Override
    public List<String> selectSixId() {
        return categoryInfoDao.selectSixId();
    }
}
