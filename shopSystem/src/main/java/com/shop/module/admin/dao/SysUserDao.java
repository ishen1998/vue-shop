package com.shop.module.admin.dao;

import java.util.List;

import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.entity.user.SysUserData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * DAO公共基类，由MybatisGenerator自动生成请勿修改
 */
@Mapper
public interface SysUserDao {
    /**
     * 根据用户id删除
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(String userId);

    /**
     * 插入Sysuser
     * @param record
     * @return
     */
    int insert(SysUser record);

    int insertSelective(SysUser record);

    /**
     * 根据id查询 Sysuser
     * @param userId
     * @return
     */
    SysUser selectByPrimaryKey(String userId);

    /**
     * 根据username 查询Sysuser
     * @param userName
     * @return
     */
    SysUser selectUserName(String userName);

    /**
     * 根据userId更新sysuser
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 根据手机号更新sysuser
     * @param record
     * @return
     */
    int updateByPhoneKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 根据useId查询角色信息
     * @param record
     * @return 角色lIST
     */
    List<SysRole> selectUserSysRole(@Param("userId") String record);

    /**
     * 查询所有角色
     * @return
     */
    List<SysUser> selectAllUser();

    SysUserData selectUser(String userId);

    /**
     * 通过userId获取角色id
     *
     * @param userId
     * @return
     */
    Integer[] selectUserRoleId(String userId);

    /**
     * 通过UserId 获取拥有的权限
     *
     * @param userId
     * @return
     */
    List<String> selectUserPermission(String userId);

    /**
     * 通过UserId 获取拥有的权限
     *
     * @param userId
     * @return
     */
    List<String> selectUserPermissionId(String userId);

    /**
     * 统计SysUser表有多少条记录
     *
     * @return SysUser 记录数
     */
    long countSysUser();

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    SysUser checkUserName(String userName);

    /**
     * 根据phone 查询用户
     * @param phone
     * @return
     */
    SysUser selectPhone(String phone);

    SysUser selectUserNamePhone(String userName,String phone);

}