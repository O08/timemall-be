package com.norm.timemall.app.base.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机验证码认证信息，在UsernamePasswordAuthenticationToken的基础上添加属性 手机号、验证码
 */
public class WechatQrCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 530L;
    private Object principal;
    private Object credentials;
    private String code;
    private String state;


    public WechatQrCodeAuthenticationToken(String code, String state) {
        super(null);
        this.code = code;
        this.state = state;
        this.setAuthenticated(false);
    }

    public WechatQrCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
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

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
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

