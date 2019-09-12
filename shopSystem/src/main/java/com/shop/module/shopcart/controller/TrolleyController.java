package com.shop.module.shopcart.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.shopcart.entity.BatchTrolley;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;
import com.shop.module.shopcart.entity.TrolleyInfoListEntity;
import com.shop.module.shopcart.service.TrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**购物车
 * @author zhoulanzhen
 * @date 2019/8/001 17:14
 */
@RestController
@Validated
@RequestMapping(value = "Trolley")
public class TrolleyController {

    @Autowired
    private TrolleyService trolleyService;

    /**
     * 购物车添加商品
     * @param trolleyInfoEntity
     * @return
     * @throws CustomizeExp
     */
    @PostMapping
    public CommonResult insertTrolley(@RequestBody @Validated TrolleyInfoEntity trolleyInfoEntity) throws CustomizeExp {
        int i = trolleyService.insertTrolley(trolleyInfoEntity);
        return CommonResult.success(i);
    }

    /**
     * 客户id查询购物车数据
     * @param cId
     * @return
     */
    @GetMapping("/{cId}")
    public CommonResult selectTrolley(@NotBlank(message = "客户ID不能为空") @PathVariable String cId) {
        List<TrolleyInfoListEntity> trolleyInfoEntities = trolleyService.selectTrolley(cId);
        return CommonResult.success(trolleyInfoEntities);
    }

    /**
     * 删除指定购物车商品
     * @param cId
     * @param itemId
     * @return
     * @throws CustomizeExp
     */
    @DeleteMapping("/{cId}/{itemId}")
    public CommonResult deleteTrolley(@PathVariable @NotBlank(message = "客户ID不能为空")String cId,
                                      @PathVariable @NotBlank(message = "商品ID不能为空") String itemId) throws CustomizeExp {
        return CommonResult.success(trolleyService.deleteTrolley(cId, itemId));
    }

    /**
     * 批量删除购物车商品
     * @param batchTrolley
     * @return
     * @throws CustomizeExp
     */
    @DeleteMapping("/batchTrolley")
    public CommonResult batchDeleteItem(@RequestBody @Validated BatchTrolley batchTrolley) throws CustomizeExp {
        System.out.println();
        return CommonResult.success(trolleyService.batchDeleteItem(batchTrolley));
    }

    /**
     * 删除指定购物车
     * @param cId
     * @param trolleyId
     * @return
     * @throws CustomizeExp
     */
    @DeleteMapping("/TrolleyId/{cId}/{trolleyId}")
    public CommonResult deleteTrolleyId(@PathVariable @NotBlank(message = "客户ID不能为空")String cId,
                                      @PathVariable @NotBlank(message = "购物车ID不能为空") String trolleyId) throws CustomizeExp {
        return CommonResult.success(trolleyService.deleteTrolleyId(cId, trolleyId));
    }

    /**
     * 批量删除购物车
     * @param batchTrolley
     * @return
     * @throws CustomizeExp
     */
    @PostMapping("/batchTrolleyId")
    public CommonResult batchDeleteTrolley(@RequestBody @Validated BatchTrolley batchTrolley) throws CustomizeExp {
        return CommonResult.success(trolleyService.batchDeleteTrolley(batchTrolley));
    }


    /**
     * 清空购物车商品
     * @param cId
     * @return
     * @throws CustomizeExp
     */
    @DeleteMapping("{cId}")
    public CommonResult emptyTrolley(@NotBlank(message = "客户ID不能为空") @PathVariable String cId) throws CustomizeExp {
        return CommonResult.success(trolleyService.emptyTrolley(cId));
    }
}
