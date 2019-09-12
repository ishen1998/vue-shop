package com.shop.module.admin.entity.role;

import com.shop.module.admin.componet.paramverifica.CustomCheck;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

/**
 * @author zhoulanzhen
 * @date 2019/7/025 15:31
 */
@Getter
@Setter
public class AddRolePermission {
    @Valid
    private SysRole sysRole;
    @CustomCheck
    private Integer[] permissionIds;

}
