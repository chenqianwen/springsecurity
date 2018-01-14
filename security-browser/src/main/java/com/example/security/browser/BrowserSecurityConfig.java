package com.example.security.browser;

import com.example.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.SmsCodeFilter;
import com.example.security.core.validate.code.ValidateCodeFilter1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authFailureHandler;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

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

        ValidateCodeFilter1 validateCodeFilter = new ValidateCodeFilter1();
        validateCodeFilter.setAuthenticationFailureHandler(authFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(authFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        // 默认的登陆页面
        String defaultLoginPage = securityProperties.getBrowser().getLoginPage();
        logger.info("默认的登陆页面："+defaultLoginPage);

        // 默认的记住我的时间
        int defaultRememberMeSeconds = securityProperties.getBrowser().getRememberMeSeconds();
        logger.info("默认的记住我的时间："+defaultRememberMeSeconds);
        /**
         *  http.httpBasic() : 浏览器中该验证是一个弹窗,登录验证
         *  http.formLogin() : 浏览器中该验证是跳转到一个form表单,登录验证
         */

        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
              // 在UsernamePasswordAuthenticationFilter过滤器之前增加smsCodeFilter，validateCodeFilter过滤器
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
                .loginPage("/authentication/require")// 指定跳转到的url
                .loginProcessingUrl("/authentication/form")//登录请求拦截的url,也就是form表单提交时指定的action
                .successHandler(authSuccessHandler)//表单登陆之后调用自定义的 认证成功处理器
                .failureHandler(authFailureHandler)//表单登陆之后调用自定义的 认证失败处理器
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(defaultRememberMeSeconds)
                .userDetailsService(userDetailsService)
                .and()
            .authorizeRequests()
                .antMatchers("/authentication/require",defaultLoginPage,"/code/*").permitAll()//匹配该url则不需要验证
                .anyRequest()
                .authenticated()
                .and()
            .csrf().disable()//禁用跨站请求伪造功能
            .apply(smsCodeAuthenticationSecurityConfig);//新增自定义配置
    }
}
