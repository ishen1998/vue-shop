package com.shop.module.customerInfo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author zhoulanzhen
 * @date 2019/8/012 14:05
 */
public class CustomerInfoDetails implements UserDetails {

    private static final String status = "Y";
    private CustomerInfo customerInfo;

    public CustomerInfoDetails(){}

    public CustomerInfoDetails(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @Override
    public boolean isEnabled() {
        if (status.equals(customerInfo.getStatus())){
            return true;
        }
        return false;
    }
}
