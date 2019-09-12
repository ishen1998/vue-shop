package com.shop.module.admin.entity.role;

import com.shop.module.admin.componet.paramverifica.CustomCheck;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 16:03
 */
@Getter
@Setter
public class UpdateRolePermission {
    private String roleId;
//    @CustomCheck
    private Integer[] permissionIds;
}
