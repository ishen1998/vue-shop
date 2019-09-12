package com.shop.module.admin.entity.role;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoulanzhen
 * @date 2019/8/007 20:27
 */
@Getter
@Setter
public class SysRolePaging {
    private String userId;
    private String sysRoleId;
    private String sysRoleName;
    private String sysRoleNickname;
}
