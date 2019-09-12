package com.shop.module.admin.service.impl;

import com.shop.config.RedisConfig;
import com.shop.module.admin.dao.SysRoleDAO;
import com.shop.module.admin.dao.SysUserDao;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.user.AdminDetails;
import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 10:20
 */
@Log4j2
@Service
public class QueryAdminDetails {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户权限信息
     * @param userId 用户id
     * @return List<GrantedAuthority>
     */
//    @Cacheable(value = "UserAuthorities", key = "#userId")
    @Cacheable(cacheNames = "UserAuthorities", key = "#userId")
    public List<GrantedAuthority> grantedAuthorities(String userId) {
        List<String> strings = sysUserDao.selectUserPermission(userId);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", strings));
        return grantedAuthorities;
    }

    @Cacheable(cacheNames = "UserDetails", key = "#userId")
    public SysUser getSysUser(String userId) {
        return sysUserService.selectByPrimaryKey(userId);
    }


    @CacheEvict(cacheNames = "UserAuthorities", key = "#userId")
    public void clearCacheUserAuthorities(String userId) {
        log.info("Redis" + "清除==>UserId===>" + userId);
    }
}
