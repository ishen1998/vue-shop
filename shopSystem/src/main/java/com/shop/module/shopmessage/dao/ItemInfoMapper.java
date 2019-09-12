package com.shop.module.shopmessage.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.shopmessage.entity.ItemInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2019/7/29 9:08
 * 分页
 */
@Mapper
public interface ItemInfoMapper extends BaseMapper<ItemInfo> {

    /**
     * 分页查询商品列表
     * @param page
     * @return
     */
    IPage<ItemInfo> selectItemInfoList(Page page);

    /**
     * 模糊查询分类ID，商品名称，产地
     * @param page
     * @param categoryId
     * @param itemName
     * @return
     */
    IPage<JSONObject> selectItemInfoShop(Page page, String itemName, String categoryId);

    /**
     * 查询商品分类ID（必填）和商品名称查询（选填）
     */
    IPage<ItemInfo> getShopCategoryName(Page page,String categoryId,String itemName);

}
