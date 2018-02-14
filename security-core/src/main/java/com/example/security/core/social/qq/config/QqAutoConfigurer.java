package com.example.security.core.social.qq.config;

import com.example.security.core.properties.QqProperties;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.social.qq.connect.QqConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 当配置文件中app-id配置值之后，所有的配置才会生效
 */
@Configuration
@ConditionalOnProperty(prefix = "example.security.social.qq",name = "app-id")
public class QqAutoConfigurer extends SocialAutoConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QqProperties qqConfig = securityProperties.getSocial().getQq();
        return new QqConnectionFactory(qqConfig.getProviderId(),qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
