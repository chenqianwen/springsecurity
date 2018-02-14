package com.example.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 默认的授权配置管理器
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
        boolean existAnyRequestConfig = false;
        String existAnyRequestConfigName = null;

        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(config);
            if (existAnyRequestConfig && currentIsAnyRequestConfig) {
                throw new RuntimeException("重复的anyRequest配置:" + existAnyRequestConfigName + ","
                          + authorizeConfigProvider.getClass().getSimpleName());
            } else if (currentIsAnyRequestConfig) {
                existAnyRequestConfig = true;
                existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
            }
        }

        if(!existAnyRequestConfig){
            // 除了上述配置的其他的都要认证
            config.anyRequest().authenticated();
        }
    }
}
