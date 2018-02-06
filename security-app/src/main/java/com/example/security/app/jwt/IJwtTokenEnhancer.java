package com.example.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/**
 * created by ygl on 2018/1/28
 */
public class IJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        HashMap<String, Object> info = new HashMap<>();
        info.put("company","lloveyou");

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);

        return accessToken;
    }
}
