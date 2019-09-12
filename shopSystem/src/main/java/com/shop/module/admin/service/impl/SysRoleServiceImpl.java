package com.shop.module.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.shop.module.admin.componet.paramverifica.CustomCheck;
import com.shop.module.admin.dao.SysRoleDAO;
import com.shop.module.admin.entity.PermissionEntity;
import com.shop.module.admin.entity.role.AddRolePermission;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.role.SysRolePermission;
import com.shop.module.admin.service.SysRoleService;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/021 10:58
 */
@Log4j2
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private SysRoleDAO sysRoleDAO;
    private QueryAdminDetails queryAdminDetails;

    @Autowired
    public SysRoleServiceImpl(SysRoleDAO sysRoleDAO, QueryAdminDetails queryAdminDetails) {
        this.sysRoleDAO = sysRoleDAO;
        this.queryAdminDetails = queryAdminDetails;
    }

    /**
     * 清空角色与权限的关系
     *
     * @param sysRoleId
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String sysRoleId) throws CustomizeExp {
        List<String> strings = sysRoleDAO.selectRoleRelationUser(sysRoleId);
        if (strings.size()!=0){
            throw new CustomizeExp("角色与用户已绑定无法删除");
        }
//        清空角色与用户的关联信息
        sysRoleDAO.deleteUserRoleRelation(sysRoleId);
        //清空角色权限关系
        sysRoleDAO.deleteUserRolePermission(sysRoleId);
        int i = sysRoleDAO.deleteByPrimaryKey(sysRoleId);
        if (i == 0) {
            throw new CustomizeExp("删除失败");
        }
        return i;
    }

    /**
     * 添加角色
     *
     * @param record 角色实体
     * @return
     */
    @Override
    public int insert(SysRole record) {
        int insert = sysRoleDAO.insert(record);
        return insert;
    }

    /**
     * 添加角色
     *
     * @param record 角色实体
     * @return
     */
    @Override
    public int insertSelective(SysRole record) {
        int i = sysRoleDAO.insertSelective(record);
        return i;
    }



    /**
     * 角色id查询角色
     *
     * @param sysRoleId 角色id
     * @return 角色实体SysRole
     */
    @Override
    public SysRole selectByPrimaryKey(String sysRoleId) {
        SysRole sysRole = sysRoleDAO.selectByPrimaryKey(sysRoleId);
        return sysRole;
    }

    /**
     * 更新角色
     *
     * @param addRolePermission
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(AddRolePermission addRolePermission) throws CustomizeExp {
        sysRoleDAO.deleteUserRolePermission(addRolePermission.getSysRole().getSysRoleId());
        int i = sysRoleDAO.updateByPrimaryKeySelective(addRolePermission.getSysRole());
        int p = sysRoleDAO.insertRolePermission(addRolePermission.getSysRole().getSysRoleId(), addRolePermission.getPermissionIds());
        if (i == 0) {
            throw new CustomizeExp("更新角色信息失败");
        }
        if (p == 0) {
            throw new CustomizeExp("更新角色权限失败");
        }
        //清楚redis中的角色权限缓存
        clearUserCache(addRolePermission.getSysRole().getSysRoleId());
        return i;
    }

    /**
     * 清除redis中用户与该角色关联的权限缓存
     * @param roleId
     */
    private void clearUserCache(String roleId) {
        List<String> userIds = sysRoleDAO.selectRoleUser(roleId);
        for (String userId : userIds) {
            queryAdminDetails.clearCacheUserAuthorities(userId);
        }
    }

    @Override
    public int updateByPrimaryKey(SysRole record) {
        int i = sysRoleDAO.updateByPrimaryKey(record);
        return i;
    }

    @Override
    public List<SysRole> selectAllSysRole() {
        List<SysRole> sysRoles = sysRoleDAO.selectAllRole();
        return sysRoles;
    }

    /**
     * 添加角色与权限信息
     * @param roleNickname
     * @param permissionIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertRolePermission(String roleNickname, Integer[] permissionIds) throws CustomizeExp {
        SysRole sysRole = new SysRole();
        sysRole.setSysRoleId(IdUtil.randomUUID());
        sysRole.setSysRoleNickname(roleNickname);
        if (sysRoleDAO.selectRoleByName(roleNickname) != null) {
            throw new CustomizeExp("角色名重复");
        }
        sysRoleDAO.insert(sysRole);
        int i = sysRoleDAO.insertRolePermission(sysRole.getSysRoleId(), permissionIds);
        return i;
    }

    /**
     * 更新角色与权限
     * @param roleId
     * @param permissionIds
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateRolePermission(String roleId, Integer[] permissionIds) throws CustomizeExp {
        sysRoleDAO.deleteUserRolePermission(roleId);
        int i = sysRoleDAO.insertRolePermission(roleId, permissionIds);
        if (i == 0) {
            throw new CustomizeExp("权限更新失败");
        }
        //清除用户redis的权限缓存
        clearUserCache(roleId);
        return i;
    }

    /**
     * 获取所有权限信息
     * @return
     */
    @Override
    public List<PermissionEntity> selectPermission() {
        List<PermissionEntity> permissionEntities = sysRoleDAO.selectPermission();
        return permissionEntities;
    }

    /**
     *  根据角色ID查询权限
     * @param roleId
     * @return
     */
    @Override
    public Integer[] selectRolePermissionId(String roleId) {
        Integer[] integers = sysRoleDAO.selectRolePermissionId(roleId);
        return integers;
    }

    /**
     * 获取角色与权限信息
     * @return
     */
    @Override
    public List<SysRolePermission> querySysRolePermission() {
        return sysRoleDAO.queryRoleData();
    }
}
