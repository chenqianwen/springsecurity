package com.example.security.app;

import com.example.security.app.social.openid.OpenIdAuthenticationSecurityConfig;
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
 * @author： yl
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


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                  .and()
            .apply(iSpringSocialConfigurer)
                  .and()
            .apply(openIdAuthenticationSecurityConfig)
                  .and()
            .formLogin()
            .loginPage("/authentication/require")// 指定的登录页面Url
            .loginProcessingUrl("/authentication/form")//登录请求拦截的url,也就是form表单提交时指定的action
            .successHandler(iAuthenticationSuccessHandler)//表单登陆之后调用自定义的 认证成功处理器
            .failureHandler(iAuthenticationFailureHandler)//表单登陆之后调用自定义的 认证失败处理器
            .and()
            .authorizeRequests()
            .and()
            .csrf().disable()//禁用跨站请求伪造功能
        ;
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
