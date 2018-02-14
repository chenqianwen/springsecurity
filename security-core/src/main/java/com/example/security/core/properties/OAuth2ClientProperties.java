package com.example.security.core.properties;

import lombok.Data;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 认证服务器注册的第三方应用配置项
 */
@Data
public class OAuth2ClientProperties {

    /**
     * 第三方应用appId
     */
    private String clientId;
    /**
     * 第三方应用appSecret
     */
    private String clientSecret;
    /**
     * 针对此应用发出的token的有效时间
     */
    private int accessTokenValiditySeconds;
}
