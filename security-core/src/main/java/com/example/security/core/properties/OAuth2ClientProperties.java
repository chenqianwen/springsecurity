package com.example.security.core.properties;

import lombok.Data;

/**
 * created by ygl on 2018/1/27
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValiditySeconds;
}
