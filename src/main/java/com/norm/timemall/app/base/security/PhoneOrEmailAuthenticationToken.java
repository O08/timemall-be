package com.norm.timemall.app.base.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机验证码认证信息，在UsernamePasswordAuthenticationToken的基础上添加属性 手机号、验证码
 */
public class PhoneOrEmailAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 530L;
    private Object principal;
    private Object credentials;
    private String username;
    private String password;


    public PhoneOrEmailAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
        this.setAuthenticated(false);
    }

    public PhoneOrEmailAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}

