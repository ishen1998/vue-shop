package com.shop.module.admin.entity.role;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoulanzhen
 * @date 2019/7/019 13:10
 */
@Getter
@Setter
public class SysRole {

    private String sysRoleId;
    @NotBlank(message = "角色名不能为空")
    private String sysRoleName;
    @NotBlank(message = "角色昵称不能为空")
    private String sysRoleNickname;
}
