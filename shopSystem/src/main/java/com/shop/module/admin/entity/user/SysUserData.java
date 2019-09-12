package com.shop.module.admin.entity.user;

import com.shop.module.admin.entity.role.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/022 17:00
 */
@Data
public class SysUserData {
    /**
     * 主键
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 年龄
     */
    private String phone;


    private String status;


    private List<SysRole> sysRoleList;
}
