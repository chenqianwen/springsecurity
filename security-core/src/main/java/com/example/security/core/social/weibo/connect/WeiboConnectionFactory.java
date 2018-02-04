package com.example.security.core.social.weibo.connect;

import com.example.security.core.social.qq.api.QQ;
import com.example.security.core.social.qq.connect.QQAdapter;
import com.example.security.core.social.qq.connect.QQServiceProvider;
import com.example.security.core.social.weibo.api.Weibo;
import com.example.security.core.social.weixin.api.Weixin;
import com.example.security.core.social.weixin.connect.WeixinAccessGrant;
import com.example.security.core.social.weixin.connect.WeixinAdapter;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class WeiboConnectionFactory extends OAuth2ConnectionFactory<Weibo> {

    public WeiboConnectionFactory(String providerId, String appKey,String appSecret) {
        super(providerId, new WeiboServiceProvider(appKey, appSecret), new WeiboAdapter());
    }

    /**
     * 由于微博的uid是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WeiboAccessGrant) {
            return ((WeiboAccessGrant)accessGrant).getUid();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.oauth2.AccessGrant)
     */
    public Connection<Weibo> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<Weibo>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
     */
    public Connection<Weibo> createConnection(ConnectionData data) {
        return new OAuth2Connection<Weibo>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<Weibo> getApiAdapter(String uid) {
        return new WeiboAdapter(uid);
    }

    private OAuth2ServiceProvider<Weibo> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Weibo>) getServiceProvider();
    }

}
