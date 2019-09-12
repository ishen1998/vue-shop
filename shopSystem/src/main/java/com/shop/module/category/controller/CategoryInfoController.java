package com.shop.module.category.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.category.entity.CategoryInfo;
import com.shop.module.category.service.CategoryInfoService;
import com.shop.module.shopmessage.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@RequestMapping("/category")
@RestController
@Validated
public class CategoryInfoController {
    @Autowired
    private CategoryInfoService categoryInfoService;
    private ItemInfoService itemInfoService;

    //新增分类
    @PreAuthorize("hasAuthority('shop')")
    @PostMapping("/insert")
    public CommonResult insert(@RequestBody CategoryInfo categoryInfo) throws CustomizeExp {
        categoryInfoService.insert(categoryInfo);
        return CommonResult.success(categoryInfo);
    }

    //编辑
    @PreAuthorize("hasAuthority('shop')")
    @PutMapping("/update")
    public CommonResult update(@RequestBody @Validated CategoryInfo categoryInfo)throws CustomizeExp{
        categoryInfoService.update(categoryInfo);
        return CommonResult.success(categoryInfo);
    }

    //根据id删除
    @PreAuthorize("hasAuthority('shop')")
    @RequestMapping("/deleteById")
    public CommonResult deleteById(@NotBlank(message = "分类ID不能为空") String categoryId) throws CustomizeExp{
        categoryInfoService.deleteById(categoryId);
        return CommonResult.success("删除成功");
    }

    //page第几页，pageSize：一页多少条数据
    //查询所有并返回列表
    @RequestMapping("/queryAll")
    public CommonResult queryAll(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
            throws CustomizeExp{
        PageHelper.startPage(page,pageSize);
        List<CategoryInfo> categoryInfos = categoryInfoService.queryAll();
        PageInfo pageInfo = new PageInfo(categoryInfos);
        return CommonResult.success(pageInfo);
    }

    //根据分类名查询
    @RequestMapping("/selectByName")
    public CommonResult selectByName(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                   @NotBlank(message = "分类名称不能为空") String categoryName) throws CustomizeExp {
        PageHelper.startPage(page,pageSize);
        return CommonResult.success(categoryInfoService.selectByName(categoryName));
    }

    //根据列表名称查询
    @RequestMapping("/selectByParent")
    public CommonResult selectByParent(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                       @NotBlank(message = "父分类名称不能为空") String listParent) throws CustomizeExp{
        PageHelper.startPage(page,pageSize);
        List<CategoryInfo> categoryInfos=categoryInfoService.selectByParent(listParent);
        PageInfo pageInfo = new PageInfo(categoryInfos);
        return CommonResult.success(pageInfo);
    }

    //根据Id查询
    @RequestMapping("/selectById")
    public CommonResult selectById(@NotBlank(message = "分类ID不能为空") String categoryId) throws CustomizeExp{
        return CommonResult.success(categoryInfoService.selectById(categoryId));
    }

    //分类名模糊查询
    @RequestMapping("/selectCategoryName")
    public CommonResult selectCategoryName(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                           @NotBlank(message = "分类名称不能为空") String categoryName) throws CustomizeExp{
        PageHelper.startPage(page,pageSize);
        List<CategoryInfo> categoryInfos=categoryInfoService.selectCategoryName(categoryName);
        PageInfo pageInfo = new PageInfo(categoryInfos);
        return CommonResult.success(pageInfo);
    }

    //根据Id修改状态
    @PreAuthorize("hasAuthority('shop')")
    @RequestMapping("/updateStatus")
    public CommonResult updateStatus(@Validated CategoryInfo categoryInfo) throws CustomizeExp{
        categoryInfoService.updateStatus(categoryInfo);
        return CommonResult.success(categoryInfo);
    }

    //根据分类id查询
    @RequestMapping("/SelectItemById")
    public CommonResult SelectItemById(@NotBlank(message = "分类Id不能为空") String categoryId, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize) throws CustomizeExp{
        return CommonResult.success(categoryInfoService.SelectItemById(categoryId));
    }

    //查询数据条数
    @RequestMapping("/selectCount")
    public CommonResult selectCount(){
        return CommonResult.success(categoryInfoService.selectCount());
    }

    //获取大分类名称
   @RequestMapping("/getListParent")
    public CommonResult getListParent(){
        return CommonResult.success(categoryInfoService.selectAllCategory());

    }

    //查询大分类下的所有商品
    @RequestMapping("/selectAllParentItem")
    public CommonResult selectAllParentItem(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                  @NotBlank(message = "父分类名称不能为空") String listParent) throws CustomizeExp{
        return CommonResult.success(categoryInfoService.selectAllParentItem(listParent));
    }

    //获取分类名称与id
    @RequestMapping("/selectCategoryNameId")
    public CommonResult selectCategoryNameId(){
        return CommonResult.success(categoryInfoService.selectCategoryNameId());

    }

}
