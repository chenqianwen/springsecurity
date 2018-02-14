package com.example.security.core.social.weibo.connect;
import com.example.security.core.social.weibo.api.Weibo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
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

    @Override
    public Connection<Weibo> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<Weibo>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
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
