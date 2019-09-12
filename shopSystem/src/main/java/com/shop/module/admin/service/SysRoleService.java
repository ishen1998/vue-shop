package com.shop.module.admin.service;

import com.shop.module.admin.entity.PermissionEntity;
import com.shop.module.admin.entity.role.AddRolePermission;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.role.SysRolePermission;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;

import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/021 10:58
 */
public interface SysRoleService {
    int deleteByPrimaryKey(String sysRoleId) throws CustomizeExp;

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String sysRoleId);

    int updateByPrimaryKeySelective(AddRolePermission addRolePermission) throws CustomizeExp;

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAllSysRole();

    int insertRolePermission(String roleNickname, Integer[] permissionIds) throws CustomizeExp;

    int updateRolePermission(String roleId, Integer[] permissionIds) throws CustomizeExp;

    List<PermissionEntity> selectPermission();

    /**
     * 根据角色ID查询拥有权限
     *
     * @param roleId
     * @return
     */
    Integer[] selectRolePermissionId(String roleId);

    List<SysRolePermission> querySysRolePermission();
}
