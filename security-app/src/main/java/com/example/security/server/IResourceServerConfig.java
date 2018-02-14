package com.example.security.server;

import com.example.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.example.security.core.authentication.FormAuthenticationConfig;
import com.example.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.example.security.core.authorize.AuthorizeConfigManager;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class IResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler iAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler iAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer iSpringSocialConfigurer;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 表单认证配置
        formAuthenticationConfig.configure(http);

        http
            // 验证码的配置
            .apply(validateCodeSecurityConfig)
                .and()
            // 验证码认证的配置
            .apply(smsCodeAuthenticationSecurityConfig)
                  .and()
            // 社交的配置
            .apply(iSpringSocialConfigurer)
                  .and()
            // openId认证的配置
            .apply(openIdAuthenticationSecurityConfig)
                  .and()
           //禁用跨站请求伪造功能
            .csrf().disable()
        ;
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
