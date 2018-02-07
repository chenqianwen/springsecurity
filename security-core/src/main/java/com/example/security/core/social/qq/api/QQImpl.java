package com.example.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 需要声明 多实例的类
 * 若果用@Component则变成单例，AbstractOAuth2ApiBinding中的全局变量accessToken会有线程安全问题
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        /**
         * 父类的一个参数构造函数：默认的token策略：TokenStrategy.AUTHORIZATION_HEADER 将access_token放到请求头
         *
         * TokenStrategy.ACCESS_TOKEN_PARAMETER 策略是将access_token作为查询参数, 所以不需要URL_GET_USERINFO请求中放处理access_token
         */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);

        String result = getRestTemplate().getForObject(url,String.class);

        System.out.println("qq返回值："+URL_GET_OPENID+result);

        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO,appId,openId);

        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println("QQUserInfo"+result);

        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败");
        }
        return userInfo;
    }
}
