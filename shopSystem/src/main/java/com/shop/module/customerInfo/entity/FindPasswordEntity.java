package com.shop.module.customerInfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author zhoulanzhen
 * @date 2019/8/020 16:33
 */
@Getter @Setter
public class FindPasswordEntity {
    @Email(message = "邮箱格式错误")
    private String email;
    @NotEmpty(message = "用户名不能为空")
    private String name;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "验证码不能为空")
    private String code;

}
