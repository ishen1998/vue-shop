package com.shop.module.customerInfo.service.Imp;

import com.shop.module.customerInfo.entity.CustomerInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhoulanzhen
 * @date 2019/8/013 8:39
 */
public class CustomerDetailsImpl implements UserDetails {

    private CustomerInfo customerInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return customerInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return customerInfo.getcLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
