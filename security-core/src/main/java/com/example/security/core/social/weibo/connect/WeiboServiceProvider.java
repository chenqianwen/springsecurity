package com.example.security.core.social.weibo.connect;

import com.example.security.core.social.weibo.api.Weibo;
import com.example.security.core.social.weibo.api.WeiboImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class WeiboServiceProvider extends AbstractOAuth2ServiceProvider<Weibo> {

    /**
     * 获取授权码的URL
     */
    private static final String URL_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";

    /**
     * 获取Access Token的URL
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token";

    public WeiboServiceProvider(String appkey,String appSecret) {
        super(new WeiboOAuth2Template(appkey,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }

    @Override
    public Weibo getApi(String accessToken) {
        return new WeiboImpl(accessToken);
    }


}
