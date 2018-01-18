package com.example.security.core.social.qq.config;

import com.example.security.core.properties.QQProperties;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 当配置文件中app-id配置值之后，所有的配置才会生效
 */
@Configuration
@ConditionalOnProperty(prefix = "security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocialProperties().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(),qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
