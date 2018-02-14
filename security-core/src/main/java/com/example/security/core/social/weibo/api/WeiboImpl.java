package com.example.security.core.social.weibo.api;

import com.alibaba.fastjson.JSONObject;
import com.example.security.core.social.qq.api.QQ;
import com.example.security.core.social.qq.api.QQUserInfo;
import com.example.security.core.social.weixin.api.WeixinUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Slf4j
public class WeiboImpl extends AbstractOAuth2ApiBinding implements Weibo {

    private static final String URL_GET_USERINFO = "https://api.weibo.com/2/users/show.json?uid=%s";

    /**
     * 要查询的用户ID。
     */
    private String uid;

    private ObjectMapper objectMapper = new ObjectMapper();

    public WeiboImpl(String accessToken) {
        /**
         * 父类的一个参数构造函数：默认的token策略：TokenStrategy.AUTHORIZATION_HEADER 将access_token放到请求头
         *
         * TokenStrategy.ACCESS_TOKEN_PARAMETER 策略是将access_token作为查询参数, 所以不需要URL_GET_USERINFO请求中放处理access_token
         */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }


    @Override
    public WeiboUserInfo getUserInfo(String uid) {

        String url = String.format(URL_GET_USERINFO,uid);

        String result = getRestTemplate().getForObject(url, String.class);

        log.info("获取到微博用户信息:" + result);

        JSONObject json = JSONObject.parseObject(result);

        WeiboUserInfo userInfo = new WeiboUserInfo();
        userInfo.setId(json.getString("id"));
        userInfo.setIdstr(json.getString("idstr"));
        userInfo.setScreen_name(json.getString("screen_name"));
        userInfo.setName(json.getString("name"));
        try {
            //userInfo = objectMapper.readValue(result, WeiboUserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败");
        }
        return userInfo;
    }

}
