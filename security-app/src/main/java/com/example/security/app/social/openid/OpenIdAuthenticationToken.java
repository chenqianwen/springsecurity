package com.example.security.app.social.openid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * created by ygl on 2018/1/27
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 存放openId
     */
    private final Object principal;
    /**
     * 服务提供商Id
     */
    @Setter
    @Getter
    private String providerId;

    public OpenIdAuthenticationToken(String openId, String providerId) {
        super((Collection)null);
        this.principal = openId;
        this.providerId = providerId;
        this.setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
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
    }
}
