package com.example.security.app;

import com.example.security.app.jwt.IJwtTokenEnhancer;
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
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 认证服务器
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
     *
     * @param endpoints 入口点
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                  .tokenStore(tokenStore)
                  .authenticationManager(authenticationManager)
                  .userDetailsService(userDetailsService);
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancers);

            endpoints
                  .tokenEnhancer(tokenEnhancerChain)
                  .accessTokenConverter(jwtAccessTokenConverter);
        }
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
                    .authorizedGrantTypes("password")
                    //请求范围
                    .scopes("al1l")
                ;
            }
        }
    }
}
