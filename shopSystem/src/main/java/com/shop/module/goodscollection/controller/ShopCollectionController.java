package com.shop.module.goodscollection.controller;

import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.goodscollection.entity.BatchDeleteCollection;
import com.shop.module.goodscollection.entity.ShopCollection;
import com.shop.module.goodscollection.service.ShopCollectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author YeLei
 */
@RestController
@RequestMapping("/collection")
public class ShopCollectionController {

    @Autowired
    private ShopCollectionService shopCollectionService;

    @ApiOperation(value = "删除指定收藏夹商品",notes = "删除指定收藏夹商品")
    @DeleteMapping("/{cId}/{itemId}")
    public CommonResult deleteByPrimaryKey(@PathVariable @NotBlank(message = "客户ID不能为空")String cId,
                                           @PathVariable @NotBlank(message = "商品ID不能为空")String itemId) throws CustomizeExp {
        int deleteByPrimaryKey = shopCollectionService.deleteByPrimaryKey(cId,itemId);
        return CommonResult.success(deleteByPrimaryKey);
    }

    @ApiOperation(value = "批量删除收藏夹商品",notes = "批量删除收藏夹商品")
    @PostMapping("/batchDelete")
    public CommonResult batchDelete(@RequestBody @Validated BatchDeleteCollection batchDeleteCollection) throws CustomizeExp {
        return CommonResult.success(shopCollectionService.batchDelete(batchDeleteCollection));
    }

    @ApiOperation(value = "添加商品到收藏夹",notes = "添加商品到收藏夹")
    @PostMapping
    public CommonResult insert(@RequestBody @Validated ShopCollection record) throws CustomizeExp {
        int insert = shopCollectionService.insert(record);
        return CommonResult.success(insert);
    }

    @ApiOperation(value = "根据客户id清空收藏夹商品",notes = "根据客户id清空收藏夹商品")
    @DeleteMapping("/{cId}")
    public CommonResult cleanAllCollection(@PathVariable @NotBlank(message = "客户ID不能为空") String cId) throws CustomizeExp {
        int i = shopCollectionService.cleanAllCollection(cId);
        return CommonResult.success(i);
    }

    @ApiOperation(value = "根据客户id查询收藏夹商品",notes = "根据客户id查询收藏夹商品")
    @GetMapping("/{cId}")
    public CommonResult selectCollectionShop(@PathVariable @NotBlank(message = "客户ID不能为空") String cId){
        List<ShopCollection> shopCollections = shopCollectionService.selectCollectionShop(cId);
        return CommonResult.success(shopCollections);
    }
}
