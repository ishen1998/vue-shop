package com.shop.module.admin.entity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/019 13:15
 */
public class AdminDetails implements UserDetails,Serializable{

    private SysUser sysUser;
    private List<GrantedAuthority> authorities;

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public AdminDetails() {
    }


    public AdminDetails(SysUser sysUser, List<GrantedAuthority> authList) {
        this.sysUser = sysUser;
        this.authorities = authList;
    }

    public AdminDetails(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public AdminDetails(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {

    }

    public String getSysUserName() {
        return sysUser.getUserName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getUserPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    private static final String status = "Y";
    @Override
    public boolean isEnabled() {
        if (status.equals(sysUser.getStatus())) {
            return true;
        }
        return false;
    }
}
