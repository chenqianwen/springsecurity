package com.example.security.core.social.qq.connect;

import com.example.security.core.social.qq.api.Qq;
import com.example.security.core.social.qq.api.QqImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class QqServiceProvider extends AbstractOAuth2ServiceProvider<Qq>{

    private String appId;

    /**
     * 获取授权码的URL
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 获取Access Token的URL
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QqServiceProvider(String appId,String appSecret) {
        super(new QqOAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public Qq getApi(String accessToken) {
        return new QqImpl(accessToken,appId);
    }


}
