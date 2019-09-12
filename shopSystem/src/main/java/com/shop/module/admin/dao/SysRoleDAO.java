package com.shop.module.admin.dao;


import com.shop.module.admin.entity.PermissionEntity;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.role.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

@Mapper
public interface SysRoleDAO {
    int deleteByPrimaryKey(String sysRoleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String sysRoleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAllRole();

    /**
     * 清空用户角色
     *
     * @param userId 用户id
     * @return
     */
    int deleteUserRoleByUserId(String userId);

    /**
     * 根据用户id添加角色信息
     *
     * @param userId
     * @param roleIds
     * @return
     */
    int insertList(String userId, List<String> roleIds);

    /**
     * 根据角色ID查询拥有权限
     *
     * @param roleId
     * @return
     */
    Integer[] selectRolePermissionId(String roleId);

    /**
     * 根据角色id添加权限信息
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    int insertRolePermission(String roleId, Integer[] permissionIds);

    /**
     * 清空角色权限信息
     *
     * @param userId
     * @return
     */
    int deleteUserRolePermission(String userId);

//    int updateRolePermission(SysRole sysRole,Integer[] permissionIds);

    /**
     * 获取所有权限信息
     * @return
     */
    List<PermissionEntity> selectPermission();

    /**
     * 删除角色与用户的关联
     * @param roleId
     * @return
     */
    int deleteUserRoleRelation(String roleId);

    /**
     * 查询所有角色信息包过权限
     * @return SysRolePermission
     */
    List<SysRolePermission> queryRoleData();

    List<String> selectRoleUser(String roleId);

    SysRole selectRoleByName(String roleNickname);

    /**
     * 查询角色已绑定的用户id
     * @param roleId
     * @return
     */
    List<String> selectRoleRelationUser(String roleId);
}