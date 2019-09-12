package com.shop.module.admin.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @author zhoulanzhen
 * @date 2019/7/020 13:51
 */
@Data
public class AdminUserInfo {
    private String userId;
    private String userName;
    private String userPassword;
    private Integer age;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String sysRoleName;

}
