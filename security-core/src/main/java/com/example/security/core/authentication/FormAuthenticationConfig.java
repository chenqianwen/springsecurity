package com.example.security.core.authentication;

import com.example.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author： ygl
 * @date： 2018/2/11-17:41
 * @Description：
 * 表单认证的配置
 */
public class FormAuthenticationConfig {

    @Autowired
    protected AuthenticationSuccessHandler iAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler iAuthenticationFailureHandler;

    public void config(HttpSecurity http) throws Exception {
        http.formLogin()
                // 未经过认证时，请求跳转的地址
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//登录请求拦截的url,也就是form表单提交时指定的action
                .successHandler(iAuthenticationSuccessHandler)//表单登陆之后调用自定义的 认证成功处理器
                .failureHandler(iAuthenticationFailureHandler);//表单登陆之后调用自定义的 认证失败处理器
    }

}
