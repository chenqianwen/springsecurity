package com.example.security.core.authentication;

import com.example.security.core.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author： ygl
 * @date： 2018/2/11-17:41
 * @Description：
 * 表单认证的配置
 */
@Component
public class FormAuthenticationConfig {

    @Autowired
    protected AuthenticationSuccessHandler iAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler iAuthenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 未经过认证时，请求跳转的地址
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                // 登录处理的url，也就是form表单提交时指定的action
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                // 认证成功处理器
                .successHandler(iAuthenticationSuccessHandler)
                // 认证失败处理器
                .failureHandler(iAuthenticationFailureHandler);
    }

}
