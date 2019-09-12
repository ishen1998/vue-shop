package com.shop.module.shopmessage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.shopmessage.entity.ItemInfo;
import com.shop.module.shopmessage.service.ItemInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *  @author YeLei
 */
@RestController
@RequestMapping("/shop")
@PreAuthorize("permitAll()")
public class ItemInfoController {

    @Autowired
    private ItemInfoService itemInfoService;

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "删除商品", notes = "根据商品ID删除")
    @DeleteMapping("/deleteByPrimaryKey")
    public CommonResult deleteByPrimaryKey(String itemId) throws CustomizeExp {
        int delete = itemInfoService.deleteByPrimaryKey(itemId);
        return CommonResult.success(delete);
    }

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "添加商品", notes = "添加一个商品全部信息")
    @PostMapping("/insert")
    public CommonResult insert(@RequestBody @Validated ItemInfo record) throws CustomizeExp {
        int insert = itemInfoService.insert(record);
        return CommonResult.success(insert);
    }


    @ApiOperation(value = "查询商品", notes = "按ID查询商品")
    @GetMapping("/selectByPrimaryKey")
    public CommonResult selectByPrimaryKey(String itemId) throws CustomizeExp {
        ItemInfo itemInfo = itemInfoService.selectByPrimaryKey(itemId);
        return CommonResult.success(itemInfo);
    }

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "更新商品", notes = "更新商品全部信息")
    @PutMapping("/updateByPrimaryKey")
    public CommonResult updateByPrimaryKey(@RequestBody @Validated ItemInfo record) throws CustomizeExp {
        int updateByPrimaryKey = itemInfoService.updateByPrimaryKey(record);
        return CommonResult.success(updateByPrimaryKey);
    }

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "选择性更新商品", notes = "只更新商品信息某些字段")
    @PutMapping("/updateByPrimaryKeySelective")
    public CommonResult updateByPrimaryKeySelective(@RequestBody @Validated ItemInfo record) throws CustomizeExp {
        int i = itemInfoService.updateByPrimaryKeySelective(record);
        return CommonResult.success(i);
    }

    @ApiOperation(value = "查询全部商品", notes = "查询全部商品信息")
    @GetMapping("/findAllShopInfo")
    public CommonResult findAllShopInfo() {
        List<ItemInfo> allShopInfo = itemInfoService.findAllShopInfo();
        return CommonResult.success(allShopInfo);
    }

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "上下架商品状态", notes = "修改上下架商品的状态")
    @GetMapping("/updateItemStatus")
    public CommonResult updateItemStatus(String itemId, String isShow) throws CustomizeExp {
        int i = itemInfoService.updatePublishStatus(itemId, isShow);
        if (i>0){
            return CommonResult.success("修改成功");
        }
        return CommonResult.failed("修改失败",null);
    }

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "新增商品", notes = "新增商品信息")
    @PostMapping("/addNewShop")
    public CommonResult addNewShop(@RequestBody @Validated ItemInfo itemInfo) throws CustomizeExp {
        ItemInfo info = itemInfoService.addNewShop(itemInfo);
        return CommonResult.success(info);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询数据库")
    @RequestMapping("/selectItemInfoList")
    public CommonResult selectItemInfoList(Integer current, Integer size) {
        IPage<ItemInfo> infoIPage = itemInfoService.selectItemInfoList(current, size);
        return CommonResult.success(infoIPage);
    }

    @ApiOperation(value = "新增商品的轮播图", notes = "轮播图")
    @RequestMapping("/shopReturnImg")
    public CommonResult shopReturnImg(String itemId) {
        List<String> list = itemInfoService.shopReturnImg(itemId);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "按商品名称查询", notes = "商品名称查询")
    @GetMapping("/getShopName")
    public CommonResult getShopName(String itemName) throws CustomizeExp {
        List<ItemInfo> shopName = itemInfoService.getShopName(itemName);
        return CommonResult.success(shopName);
    }

    @ApiOperation(value = "按商品分类ID和商品名称查询", notes = "按商品分类ID和商品名称查询")
    @GetMapping("/getShopCategoryName")
    public CommonResult getShopCategoryName(Integer current,Integer size,String categoryId,String itemName) throws CustomizeExp {
        IPage<ItemInfo> shopCategoryName = itemInfoService.getShopCategoryName(current, size, categoryId, itemName);
        return CommonResult.success(shopCategoryName);
    }

    @ApiOperation(value = "按商品生产地和商品名称查询", notes = "商品名称可选填，产地必填")
    @GetMapping("/getShopLocality")
    public CommonResult getShopLocality(String locality,String itemName) throws CustomizeExp {
        List<ItemInfo> shopName = itemInfoService.getShopLocality(locality,itemName);
        return CommonResult.success(shopName);
    }

    @ApiOperation(value = "商品名称和商品ID分页查询", notes = "商品名称和商品ID分页查询")
    @RequestMapping("/selectItemInfoShop")
    public CommonResult selectItemInfoShop(Integer current, Integer size,String itemName,String categoryId) throws CustomizeExp {
        Map<Object, Object> map = itemInfoService.selectItemInfoShop(current, size, itemName, categoryId);
        return CommonResult.success(map);
    }

    @ApiOperation(value = "商品首页产地图片", notes = "商品首页产地图片")
    @GetMapping("/shopLocalityImg")
    public CommonResult shopLocalityImg(){
        String[][] arr={{"http://192.168.3.88:8089/shop/downloadFile/4b32e3e2-2082-4944-83f1-f0e475aede3e.png","上海"},
                {"http://192.168.3.88:8089/shop/downloadFile/c2b7a6cb-151f-49be-b10f-4cb241ce0a90.png","杭州"},
                {"http://192.168.3.88:8089/shop/downloadFile/10136da4-dc70-4bef-acce-6529c06481ff.png","江西"},
                {"http://192.168.3.88:8089/shop/downloadFile/30702eee-bb84-473e-9d36-257d142f4a36.png","广东"}};
        return CommonResult.success(arr);
    }

    @PreAuthorize("hasAuthority('shop')")
    @ApiOperation(value = "上下架商品状态(纯净版)", notes = "修改上下架商品的状态(纯净版)")
    @GetMapping("/updateShopStatus")
    public CommonResult updateShopStatus(String itemId, String isShow) throws CustomizeExp {
        itemInfoService.updateShopStatus(itemId, isShow);
        return CommonResult.success("修改商品状态成功");
    }
}
