package com.example.security.core.properties;

import lombok.Data;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValiditySeconds;
}
