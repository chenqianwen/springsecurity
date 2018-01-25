package com.example.security.app;

import com.example.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.example.security.core.properties.SecurityConstants;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
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
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer iSpringSocialConfigurer;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .apply(iSpringSocialConfigurer)//新增自定义配置
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            //.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
            // 在UsernamePasswordAuthenticationFilter过滤器之前增加smsCodeFilter，validateCodeFilter过滤器
            //.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/authentication/require")// 指定的登录页面Url
            .loginProcessingUrl("/authentication/form")//登录请求拦截的url,也就是form表单提交时指定的action
            .successHandler(iAuthenticationSuccessHandler)//表单登陆之后调用自定义的 认证成功处理器
            .failureHandler(iAuthenticationFailureHandler)//表单登陆之后调用自定义的 认证失败处理器
            .and()
            .authorizeRequests()
            .antMatchers(
                    SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                    SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                    securityProperties.getBrowser().getLoginPage(),
                    SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                    securityProperties.getBrowser().getSignUpUrl(),
                    "/user/regist",
                    "/connect",
                    "/connect/*",
                    "/default-binding.html",
                    securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
                    securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html"
            )
            .permitAll()//匹配该url则不需要验证
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable()//禁用跨站请求伪造功能
        ;
    }
}
