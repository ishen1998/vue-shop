package com.shop.module.admin.entity.role;

import com.shop.module.admin.entity.PermissionEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/005 16:11
 */
@Getter
@Setter
public class SysRolePermission {
    private String sysRoleId;
    private String sysRoleName;
    private String sysRoleNickname;
    private List<PermissionEntity> listPermission;
}
