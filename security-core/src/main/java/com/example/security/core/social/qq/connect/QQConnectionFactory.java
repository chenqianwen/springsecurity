package com.example.security.core.social.qq.connect;

import com.example.security.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     *  ConnectionFactory需要的参数列表：
     *     private final String providerId;
     *     private final ServiceProvider<A> serviceProvider;
     *     private final ApiAdapter<A> apiAdapter;
     * @param providerId  服务提供商ID
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
