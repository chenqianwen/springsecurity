package com.example.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Component
public class IAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private Set<AuthorizeConfigProvider> authorizeConfigProviders;

    /**
     * 除了authorizeConfigProviders配置的请求，其他的都需要验证
     * @param config
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
//        config.anyRequest().authenticated();
    }
}
