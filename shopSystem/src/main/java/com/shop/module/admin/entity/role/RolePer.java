package com.shop.module.admin.entity.role;

import com.shop.module.admin.componet.paramverifica.CustomCheck;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoulanzhen
 * @date 2019/8/008 16:34
 */
@Getter
@Setter
public class RolePer {
    @NotBlank(message = "角色名不能为空")
    private String roleNickname;
//    @CustomCheck(message = "权限数组不能为空")
    private Integer[] permissionIds;
}
