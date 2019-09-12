package com.shop.module.admin.controller;

import com.shop.common.CommonResult;
import com.shop.module.admin.entity.PermissionEntity;
import com.shop.module.admin.entity.role.*;
import com.shop.module.admin.service.SysRoleService;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/019 19:38
 */
@PreAuthorize("hasAuthority('SettingPermissions')")
@RequestMapping("/sysuser/role")
@RestController
@Validated
public class SysRoleController {


    private SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @ApiOperation(value = "添加角色与权限", notes = "角色添加权限")
    @PostMapping("/insertRolePermission")
    public CommonResult insertRolePermission(@RequestBody @Validated RolePer rolePer) throws CustomizeExp {
        sysRoleService.insertRolePermission(rolePer.getRoleNickname(), rolePer.getPermissionIds());
        return CommonResult.success("添加成功");
    }

    @ApiOperation(value = "删除角色", notes = "根据角色ID删除角色")
    @DeleteMapping("/deleteSysRole")
    public CommonResult deleteByPrimaryKey(@NotBlank(message = "角色ID不能为空") String sysRoleId) throws CustomizeExp {
        int i = sysRoleService.deleteByPrimaryKey(sysRoleId);
        return CommonResult.success(i);
    }

//    @ApiOperation(value = "添加角色", notes = "添加角色")
//    @PostMapping("/insertSysRole")
//    public CommonResult insert(@RequestBody SysRole record) {
//        int insert = sysRoleService.insert(record);
//        return CommonResult.success(insert);
//    }

    @PreAuthorize("permitAll()")
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    @GetMapping("/AllSysRole")
    public CommonResult selectAllSysRole() {
        List<SysRole> sysRoles = sysRoleService.selectAllSysRole();
        return CommonResult.success(sysRoles);
    }



    @ApiOperation(value = "角色更新权限", notes = "更新角色权限信息")
    @PutMapping("/updateRolePermission")
    public CommonResult updateRolePermission(@RequestBody @Validated UpdateRolePermission updateRolePermission) throws CustomizeExp {
        int i = sysRoleService.updateRolePermission(updateRolePermission.getRoleId(), updateRolePermission.getPermissionIds());
        return CommonResult.success(i);
    }


    @ApiOperation(value = "查询所有权限", notes = "查询所有权限信息")
    @GetMapping("/permission")
    public CommonResult selectRolePermission() {
        List<PermissionEntity> permissionEntities = sysRoleService.selectPermission();
        return CommonResult.success(permissionEntities);
    }

    @ApiOperation(value = "根据角色ID查询权限", notes = "")
    @GetMapping("/permissionId")
    public CommonResult selectRolePermissionId(@NotBlank(message = "角色ID不能为空") String sysRoleId) {
        Integer[] integers = sysRoleService.selectRolePermissionId(sysRoleId);
        return CommonResult.success(integers);
    }

    @ApiOperation(value = "更新角色信息和权限信息", notes = "")
    @PutMapping("/sysRolePermission")
    public CommonResult updateByPrimaryKeySelective(@RequestBody AddRolePermission addRolePermission) throws CustomizeExp {
        int i = sysRoleService.updateByPrimaryKeySelective(addRolePermission);
        return CommonResult.success(i);
    }
    @ApiOperation(value = "获取角色信息和权限信息", notes = "")
    @GetMapping("/sysRolePermission")
    public CommonResult querySysRolePermission(){
        List<SysRolePermission> sysRolePermissions = sysRoleService.querySysRolePermission();
        return CommonResult.success(sysRolePermissions);
    }

}
