package com.example.security.app.social.openid;

import com.example.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * created by ygl on 2018/1/27
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter
    private SocialUserDetailsService userDetailsService;

    @Setter
    @Getter
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken)authentication;
        Set<String> providerUserIds = new HashSet<>();
        providerUserIds.add((String)authenticationToken.getPrincipal());

        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);

        if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();

        SocialUserDetails socialUserDetails = userDetailsService.loadUserByUserId(userId);

        if (socialUserDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 已认证的token
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(socialUserDetails,socialUserDetails.getAuthorities());

        // 设置已认证的token的details
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

