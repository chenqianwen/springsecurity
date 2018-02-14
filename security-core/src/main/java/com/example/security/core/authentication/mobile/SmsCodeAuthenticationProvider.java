package com.example.security.core.authentication.mobile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 提供验证逻辑
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

    @Getter
    @Setter
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;
        String mobile = (String)authenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 已认证的token
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());

        // 设置已认证的token的details
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
