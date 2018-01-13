package com.example.security.browser;

import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 表单登录 身份验证
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 默认的登陆页面
        String defaultLoginPage = securityProperties.getBrowser().getLoginPage();
        logger.info("默认的登陆页面："+defaultLoginPage);
        /**
         *  http.httpBasic() : 浏览器中该验证是一个弹窗,登录验证
         *  http.formLogin() : 浏览器中该验证是跳转到一个form表单,登录验证
         */
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authFailureHandler);
//        http.httpBasic()
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
//                  .loginPage("/signIn.html")// 指定跳转到的url
//                  .loginProcessingUrl("/authentication/form")//登录请求拦截的url,也就是form表单提交时指定的action
            .loginPage("/authentication/require")
            .loginProcessingUrl("/authentication/form")
            .successHandler(authSuccessHandler)//表单登陆之后调用自定义的 认证成功处理器
            .failureHandler(authFailureHandler)//表单登陆之后调用自定义的 认证失败处理器
            .and()
            .authorizeRequests()
            .antMatchers("/authentication/require",defaultLoginPage,
                  "/code/image").permitAll()//匹配该url则不需要验证
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();//禁用跨站请求伪造功能
    }
}
