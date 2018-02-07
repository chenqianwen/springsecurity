package com.example.security.core.social.weibo.config;

import com.example.security.core.properties.QQProperties;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.properties.WeiboProperties;
import com.example.security.core.social.qq.connect.QQConnectionFactory;
import com.example.security.core.social.weibo.connect.WeiboConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 当配置文件中app-id配置值之后，所有的配置才会生效
 */
@Configuration
@ConditionalOnProperty(prefix = "example.security.social.weibo",name = "app-id")
public class WeiboAutoConfig extends SocialAutoConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiboProperties weiboConfig  = securityProperties.getSocial().getWeibo();
        return new WeiboConnectionFactory(weiboConfig.getProviderId(),weiboConfig.getAppId(), weiboConfig.getAppSecret());
    }
}
