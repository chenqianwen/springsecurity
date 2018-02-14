package com.example.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 授权信息管理器
 * 用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 */
public interface AuthorizeConfigManager {

    /**
     * 认证配置
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
