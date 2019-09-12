package com.shop.module.category.controller;

import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.category.entity.CategoryInfo;
import com.shop.module.category.entity.CategoryLog;
import com.shop.module.category.service.CategoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RequestMapping("/a")
@RestController
public class CategoryLogController {
    @Autowired
    private CategoryLogService categoryLogService;

    //新增分类
    @PostMapping("/insertItemId")
    public CommonResult insertItemId( CategoryLog categoryLog) throws CustomizeExp {
        categoryLogService.insertItemId(categoryLog);
        return CommonResult.success(categoryLog);
    }

    @RequestMapping("/deleteAll")
    public CommonResult deleteAll(@NotBlank(message = "分类ID不能为空") String categoryId) throws CustomizeExp{
        categoryLogService.deleteAll(categoryId);
        return CommonResult.success("删除成功");
    }

    //根据Id查询
    @RequestMapping("/queryAllItemId")
    public CommonResult queryAllItemId(@NotBlank(message = "分类ID不能为空") String categoryId) throws CustomizeExp{
        return CommonResult.success(categoryLogService.queryAllItemId(categoryId));
    }
}
