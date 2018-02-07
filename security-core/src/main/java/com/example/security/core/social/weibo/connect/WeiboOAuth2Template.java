package com.example.security.core.social.weibo.connect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Slf4j
public class WeiboOAuth2Template extends OAuth2Template {

    private String uid;

    public WeiboOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 获取access_token
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        JSONObject json = getRestTemplate().postForObject(accessTokenUrl, parameters, JSONObject.class);
        String accessToken = json.getString("access_token");
        long expiresIn = json.getLongValue("expires_in");
        String uid = json.getString("uid");
        WeiboAccessGrant accessGrant = new WeiboAccessGrant(accessToken, null, null, expiresIn);
        accessGrant.setUid(uid);
        log.info("获取accessToken的响应:" + json);
        return accessGrant;
    }



    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
