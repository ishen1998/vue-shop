package com.shop.module.admin.service.impl;

import com.shop.module.admin.dao.SysUserDao;
import com.shop.module.admin.entity.user.AdminDetails;
import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @date 2019/5/016 15:10
 */

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private QueryAdminDetails adminDetails;


//    @Cacheable(cacheNames="UserDetails", key="#userId",condition="#userId.length()==32")
//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
//        if (sysUser == null) {
//            sysUser = sysUserDao.selectUserName(userId);
//        }
//        if (sysUser != null) {
////            List<String> strings = sysUserDao.selectUserPermission(sysUser.getUserId());
////            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", strings));
//            return new AdminDetails(sysUser, grantedAuthorities("19bec4b53cc14c20b90ee0d7f2f7a782"));
//        }
//        return null;
//    }

    /**
     * 实现security中的加载用户信息接口
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        SysUser sysUser = adminDetails.getSysUser(userId);
        if (sysUser != null) {
            return new AdminDetails(sysUser, adminDetails.grantedAuthorities(sysUser.getUserId()));
        }
        return null;
    }

}
