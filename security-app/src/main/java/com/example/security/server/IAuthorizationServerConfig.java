package com.example.security.server;

import com.example.security.core.properties.OAuth2ClientProperties;
import com.example.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class IAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    /**
     * 认证及token的配置
     * @param endpoints 入口点
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                  .tokenStore(tokenStore)
                  .authenticationManager(authenticationManager)
                  .userDetailsService(userDetailsService);
        /**
         * 多个TokenEnhancer实现类的时候可以用TokenEnhancerChain
         */
        if (jwtAccessTokenConverter != null || jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            if (jwtAccessTokenConverter != null) {
                enhancers.add(jwtAccessTokenConverter);
            }
            if (jwtTokenEnhancer != null) {
                enhancers.add(jwtTokenEnhancer);
            }
            tokenEnhancerChain.setTokenEnhancers(enhancers);
            endpoints
                  .tokenEnhancer(tokenEnhancerChain)
                  .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * tokenKey的访问权限表达式配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
    }
    /**
     * 客户端相关的配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        OAuth2ClientProperties[] configClients = securityProperties.getOauth2().getClients();
        if (ArrayUtils.isNotEmpty(configClients)) {
            for (OAuth2ClientProperties configClient : configClients) {
                builder
                    // clientId
                    .withClient(configClient.getClientId())
                    // clientSecret
                    .secret(configClient.getClientSecret())
                    // token有效时间
                    .accessTokenValiditySeconds(configClient.getAccessTokenValiditySeconds())
                    //支持的授权模式
                    .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                    // refreshToken有效时间
                    .refreshTokenValiditySeconds(2592000)
                    //请求范围
                    .scopes("all")
                ;
            }
        }
    }
}
