package com.example.security.core.social;


import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.crypto.EncryptedPrivateKeyInfo;
import javax.sql.DataSource;
import java.security.Provider;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    private UsersConnectionRepository usersConnectionRepository;

    /**
     * 默认的查询内存数据InMemoryUsersConnectionRepository
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /**
         * 第三个参数：加解密工具,对UserConnection数据加密
         */
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        /**
         * 设置UserConnection表的前缀
         */
//        repository.setTablePrefix("");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        usersConnectionRepository = repository;
        return repository;
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository () {
        return usersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer iSpringSocialConfigurer() {
        //return new SpringSocialConfigurer();
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        ISpringSocialConfigurer iSpringSocialConfigurer = new ISpringSocialConfigurer(filterProcessesUrl);
        // 如果没有查询到对应的社交用户信息，设置跳转到注册页面的URL
        iSpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        iSpringSocialConfigurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return iSpringSocialConfigurer;
    }

    /**
     * 这种写法自动会注入ConnectionFactoryLocator
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils (ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }
}
