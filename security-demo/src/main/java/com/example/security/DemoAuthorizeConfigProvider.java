package com.example.security;

import com.example.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/index.html")
              .hasRole("ADMIN1");
        // rbacService指的是service名称
        //config.anyRequest().access("@rbacService.hasPermission(request,Authentication)");
        // 多权限用access方法
    }
}
