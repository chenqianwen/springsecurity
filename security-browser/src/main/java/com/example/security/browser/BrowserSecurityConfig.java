package com.example.security.browser;

import com.example.security.core.authentication.FormAuthenticationConfig;
import com.example.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.example.security.core.authorize.AuthorizeConfigManager;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCodeFilter;
import com.example.security.core.validate.code.ValidateCodeSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 浏览器环境下安全配置主类
 */
@Configuration
@Slf4j
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

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

    @Autowired
    private SpringSocialConfigurer iSocialSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy iExpiredSessionStrategy;

    @Autowired
    private InvalidSessionStrategy iInvalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler iLogoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动时创建表persistent_logins
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 表单登录 身份验证
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        formAuthenticationConfig.configure(http);
        /**
         *  http.httpBasic() : 浏览器中该验证是一个弹窗,登录验证
         *  http.formLogin() : 浏览器中该验证是跳转到一个form表单,登录验证
         */
        http
            .apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .apply(iSocialSecurityConfig)
                .and()
            //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionStrategy(iInvalidSessionStrategy)
                 //session最大session数值，后面登录踢掉前面登录
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                 //达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                  //session失效策略
                .expiredSessionStrategy(iExpiredSessionStrategy)
                .and()
                .and()
            .logout()
                 //调用该url退出系统
                .logoutUrl("/signOut")
                 //退出成功跳转url
                .logoutSuccessUrl("/logout.html ")
                 //退出成功处理器 配置之后logoutSuccessUrl失效
                .logoutSuccessHandler(iLogoutSuccessHandler)
                  //退出删除cookie
                .deleteCookies("JSESSIONID")
                .and()
            .csrf().disable()//禁用跨站请求伪造功能
            ;
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
