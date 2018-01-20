package com.example.security.core.social;


import com.example.security.core.properties.SecurityProperties;
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
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.crypto.EncryptedPrivateKeyInfo;
import javax.sql.DataSource;
import java.security.Provider;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

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
        repository.setTablePrefix("");
        return repository;
    }

    @Bean
    public SpringSocialConfigurer iSpringSocialConfigurer() {
        //return new SpringSocialConfigurer();
        String filterProcessesUrl = securityProperties.getSocial().getQq().getFilterProcessesUrl();
        SpringSocialConfigurer iSpringSocialConfigurer = new ISpringSocialConfigurer(filterProcessesUrl);
        iSpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return iSpringSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils (ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }
}
