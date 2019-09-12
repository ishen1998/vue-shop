package com.shop.module.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.common.CommonResult;
import com.shop.componet.JwtTokenUtil;
import com.shop.module.admin.entity.Ty;
import com.shop.module.admin.entity.user.*;
import com.shop.module.admin.service.SysUserService;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/015 17:14
 */
@PreAuthorize("hasAuthority('SettingPermissions')")
@RequestMapping("/sysuser/")
@RestController
@Validated
public class SysUserController {
    private Logger logger = LoggerFactory.getLogger(SysUserController.class);
    private SysUserService sysUserService;
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    public SysUserController(SysUserService sysUserService, JwtTokenUtil jwtTokenUtil) {
        this.sysUserService = sysUserService;
        this.jwtTokenUtil = jwtTokenUtil;
    }



    @ApiOperation(value = "查询所有用户信息", notes = "查询所有后台用户信息")
    @GetMapping("selectAllUser")
    public CommonResult selectAllUser() {
        List<SysUser> sysUsers = sysUserService.selectAllSysUser();
        return CommonResult.success(sysUsers);
    }


    @PreAuthorize("hasAuthority('userManagement')")
    @ApiOperation(value = "删除用户", notes = "通过用户ID删除一个用户")
    @DeleteMapping("deleteByPrimaryKey")
    public CommonResult deleteByPrimaryKey(@NotBlank(message = "用户ID不能为空") String userId) throws CustomizeExp {
        int i = sysUserService.deleteByPrimaryKey(userId);
        return CommonResult.success(i);
    }



    @ApiOperation(value = "查询用户(用户id)", notes = "通过用户ID查询一个用户")
    @GetMapping("selectByPrimaryKey")
    public CommonResult selectByPrimaryKey(@NotBlank(message = "用户ID不能为空") String userId) {
        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        return CommonResult.success(sysUser);
    }

    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @PutMapping("updateByPrimaryKeySelective")
    public CommonResult updateByPrimaryKeySelective(@RequestBody UpdateUserEntity updateUserEntity) throws CustomizeExp {
        int i = sysUserService.updateByPrimaryKeySelective(updateUserEntity.getSysUser(), updateUserEntity.getRoleIds());
        if (i == 0) {
            throw new CustomizeExp("没有该条数据");
        }
        return CommonResult.success(i);
    }

    @ApiOperation(value = "查询用户角色", notes = "通过用户ID查询角色信息")
    @GetMapping("selectUserRoleId")
    public CommonResult selectUserRoleId(@NotBlank(message = "用户ID不能为空") String userId) {
        Integer[] integers = sysUserService.selectUserRoleId(userId);
        return CommonResult.success(integers);
    }

    @ApiOperation(value = "刷新Token", notes = "通过检验Token有效性刷新Token")
    @GetMapping("refreshToken")
    public CommonResult refreshToken(String token) throws CustomizeExp {
        String s = sysUserService.refreshToken(token);
        return CommonResult.success(s);
    }


    @ApiOperation(value = "更新Token数据", notes = "")
    @PutMapping("updateToken")
    public CommonResult updateToken(String token) throws CustomizeExp {
        String s = sysUserService.updateToken(token);
        return CommonResult.success(s);
    }

//    @ApiOperation(value = "用户分页查询", notes = "current 第几页 size 一页显示多少条数据")
//    @GetMapping("/queryAllUser/{current}/{size}")
//    public CommonResult queryAllUser(@PathVariable @NotNull(message = "current 不能为NULL") Integer current,
//                                     @PathVariable @NotNull(message = "size 不能为NULL")Integer size) throws CustomizeExp {
//        long count = sysUserService.countSysUser();
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("count",count);
//        map.put("SysUserList", sysUserService.queryAll(current, size));
//        return CommonResult.success(map);
//    }

//    @ApiOperation(value = "用户分页查询", notes = "current 第几页 size 一页显示多少条数据")
//    @GetMapping("/queryAllUser/{current}/{size}")
//    public CommonResult queryAllUser(@PathVariable @NotNull(message = "current 不能为NULL") Integer current,
//                                     @PathVariable @NotNull(message = "size 不能为NULL")Integer size) throws CustomizeExp {
//        long count = sysUserService.countSysUser();
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("count",count);
//        map.put("SysUserList", sysUserService.queryUserData(current, size));
//        return CommonResult.success(map);
//    }


//    @ApiOperation(value = "用户分页查询", notes = "current 第几页 size 一页显示多少条数据")
//    @GetMapping("queryAllUser/{current}/{size}")
//    public CommonResult queryAllUser(@PathVariable @NotNull(message = "current 不能为空") Integer current,
//                                     @PathVariable @NotNull(message = "size 不能为空") Integer size) throws CustomizeExp {
//        long count = sysUserService.countSysUser();
//        Map<String, Object> map = new HashMap<>(2);
//        if (count == 0) {
//            map.put("count", count);
//            map.put("SysUserList", null);
//            return CommonResult.success(map);
//        }
//        map.put("count", count);
//        map.put("SysUserList", sysUserService.queryUserData(current, size));
//        return CommonResult.success(map);
//    }

    @ApiOperation(value = "用户分页查询", notes = "current 第几页 size 一页显示多少条数据")
    @GetMapping("queryAllUser/{current}/{size}")
    public CommonResult queryAllUser(@PathVariable @NotNull(message = "current 不能为空") Integer current,
                                     @PathVariable @NotNull(message = "size 不能为空") Integer size) throws CustomizeExp {

        IPage<JSONObject> sysUserDataIPage = sysUserService.queryUserData(current, size);
        return CommonResult.success(sysUserDataIPage);
    }



    @GetMapping("checkId/{userId}")
    public CommonResult checkUserId(@PathVariable String userId) {
        return CommonResult.success(sysUserService.queryCheckUserId(userId));
    }

    @GetMapping("checkPhone/{phone}")
    public CommonResult checkUserPhone(@PathVariable String phone) {
        return CommonResult.success(sysUserService.queryCheckPhone(phone));
    }

    /**
     * 后台根据手机号修改用户权限
     * @param jsonObject
     * @return
     * @throws CustomizeExp
     */
    @PostMapping("insertUser")
    public CommonResult insertUser(@RequestBody JSONObject jsonObject) throws CustomizeExp {
        return CommonResult.success(sysUserService.insertUser(jsonObject));
    }




    @PostMapping("tn")
    public CommonResult tttt(@RequestBody Ty ty) {
        System.out.println(ty);
        return CommonResult.success(ty);
    }

}
