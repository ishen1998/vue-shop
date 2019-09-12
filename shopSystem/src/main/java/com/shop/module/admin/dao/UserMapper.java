package com.shop.module.admin.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.role.SysRolePaging;
import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.entity.user.SysUserData;
import com.shop.module.admin.entity.user.SysUserInfoPaging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/029 9:08
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectUserList(Page page);

    IPage<JSONObject> selectUserInfoList(Page page);

    List<SysRolePaging> selectUserData(List<SysUser> sysUsers);
}
