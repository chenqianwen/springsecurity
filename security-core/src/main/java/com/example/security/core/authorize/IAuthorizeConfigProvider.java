package com.example.security.core.authorize;

import com.example.security.core.properties.SecurityConstants;
import com.example.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Component
@Order(Integer.MIN_VALUE)
public class IAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                  SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                  SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                  SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                securityProperties.getBrowser().getSignInPage(),
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getSignOutUrl(),
                securityProperties.getBrowser().getSession().getSessionInvalidUrl()
                )
        .permitAll();
    }
}
