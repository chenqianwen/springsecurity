package com.example.security.core.authentication;

import com.example.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler iAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler iAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)// 指定的登录页面Url
            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//登录请求拦截的url,也就是form表单提交时指定的action
            .successHandler(iAuthenticationSuccessHandler)//表单登陆之后调用自定义的 认证成功处理器
            .failureHandler(iAuthenticationFailureHandler);//表单登陆之后调用自定义的 认证失败处理器
    }

}