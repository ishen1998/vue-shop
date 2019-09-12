package com.shop.module.admin.entity.user;

import com.shop.module.admin.entity.role.SysRolePaging;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 用户信息和角色数据分页类
 * @author zhoulanzhen
 * @date 2019/8/007 20:21
 */
@AllArgsConstructor
@Data
public class SysUserInfoPaging {

    private SysUser sysUser;
    private List<SysRolePaging> sysRoleList;

    public SysUserInfoPaging() {

    }
}
