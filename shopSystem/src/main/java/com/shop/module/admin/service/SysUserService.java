package com.shop.module.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.entity.user.SysUserData;
import com.shop.module.admin.entity.user.SysUserInfoPaging;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/7/015 17:07
 */
public interface SysUserService {
    int deleteByPrimaryKey(String userId) throws CustomizeExp;

    int insert(SysUser record,String code, List<String> roleIds) throws CustomizeExp;

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String userId);

    SysUser selectUserName(String userName);

    int updateByPrimaryKeySelective(SysUser record, List<String> roleIds) throws CustomizeExp;

    int updateByPrimaryKey(SysUser record);

    List<SysRole> selectRole(String record);

    Map<String,Object> login(String username, String password) throws CustomizeExp;

    List<SysUser> selectAllSysUser();



    Integer[] selectUserRoleId(String userId);

    /**
     * 从token中获取用户名 在通过用户名获取用户id
     * 在通过用户ID获取最新的用户权限信息
     *
     * @param token
     * @return token
     */
    String updateToken(String token) throws CustomizeExp;


    /**
     * Mybatis-plus 分页查询
     *
     * @param current
     * @param size
     * @return
     * @throws CustomizeExp
     */
    List<SysUser> queryAll(Integer current, Integer size) throws CustomizeExp;

//    List<SysUserInfoPaging> queryUserData(Integer current, Integer size);

    IPage<JSONObject> queryUserData(Integer current, Integer size);

    /**
     * 统计SysUser 表记录数
     *
     * @return SysUser 表记录数
     */
    long countSysUser();

    /**
     * userId获取权限列表
     * @param userId
     * @return
     */
    List<String> getUserGrantedAuthority(String userId);

    List<String> getUserGrantedAuthorityId(String userId);

    String queryCheckUserId(String userId);

    String queryCheckPhone(String phone);

    Integer insertUser(JSONObject jsonObject) throws CustomizeExp;

    Map<String,Object> checkToken(String token) throws CustomizeExp;

    String refreshToken(String token) throws CustomizeExp;
}
