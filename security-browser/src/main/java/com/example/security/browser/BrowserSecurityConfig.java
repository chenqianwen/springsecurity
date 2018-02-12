package com.example.security.browser;

import com.example.security.core.authentication.AbstractChannelSecurityConfig;
import com.example.security.core.authentication.FormAuthenticationConfig;
import com.example.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.example.security.core.authorize.AuthorizeConfigManager;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Configuration
@Slf4j
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

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

        //formAuthenticationConfig.configure(http);

        applyPasswordAuthenticationConfig(http);

        // 默认的记住我的时间
        int defaultRememberMeSeconds = securityProperties.getBrowser().getRememberMeSeconds();
        log.info("默认的记住我的时间："+defaultRememberMeSeconds);
        /**
         *  http.httpBasic() : 浏览器中该验证是一个弹窗,登录验证
         *  http.formLogin() : 浏览器中该验证是跳转到一个form表单,登录验证
         */
        http
            .apply(iSpringSocialConfigurer)//新增自定义社交配置
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            //.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
              // 在UsernamePasswordAuthenticationFilter过滤器之前增加smsCodeFilter，validateCodeFilter过滤器
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(defaultRememberMeSeconds)
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionStrategy(iInvalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//session最大session数值，后面登录踢掉前面登录
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(iExpiredSessionStrategy)//session失效策略
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")//调用该url退出系统
                .logoutSuccessUrl("/logout.html ")//退出成功跳转url
                .logoutSuccessHandler(iLogoutSuccessHandler)//退出成功处理器 配置之后logoutSuccessUrl失效
                .deleteCookies("JSESSIONID")//退出指定删除
                .and()
            .csrf().disable()//禁用跨站请求伪造功能
            ;
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
