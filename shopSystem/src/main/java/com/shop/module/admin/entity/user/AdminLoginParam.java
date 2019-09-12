package com.shop.module.admin.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoulanzhen
 * @date 2019/7/019 13:20
 */
@Setter
@Getter
public class AdminLoginParam {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "用户密码不能为空")
    private String password;
}
