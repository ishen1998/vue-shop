package com.shop.module.admin.controller;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.shop.common.CommonResult;
import com.shop.module.admin.entity.user.AddUserEntity;
import com.shop.module.admin.entity.user.AdminLoginParam;
import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.service.SysUserService;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/8/023 8:59
 */
@RequestMapping("/sysuser/")
@RestController
@Validated
public class SysUserLoginController {

    private SysUserService sysUserService;



    @Autowired
    public SysUserLoginController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "用户登入", notes = "登入成功返回token 此后访问受保护资源必须携带")
    @PostMapping("/login")
    public CommonResult login(@RequestBody @Validated AdminLoginParam adminLoginParam) throws CustomizeExp {
        Map<String, Object> tokenMap = sysUserService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
        return CommonResult.success(tokenMap);
    }


    @ApiOperation(value = "添加用户", notes = "添加一个用户")
    @PostMapping("insert")
    public CommonResult insert(@RequestBody @Validated AddUserEntity addUserEntity) throws MySQLIntegrityConstraintViolationException, CustomizeExp {
        SysUser sysUser = addUserEntity.getSysUser();
        sysUser.setStatus("Y");
        int i = sysUserService.insert(sysUser, addUserEntity.getCode(),addUserEntity.getRoleIds());
        return CommonResult.success(i);
    }

    @GetMapping("checkToken/{token}")
    public CommonResult checkToken(@PathVariable String token) throws CustomizeExp {
        return CommonResult.success(sysUserService.checkToken(token));
    }
}
