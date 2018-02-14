package com.example.security.core.social.qq.connect;

import com.example.security.core.social.qq.api.Qq;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class QqConnectionFactory extends OAuth2ConnectionFactory<Qq> {
    /**
     *  ConnectionFactory需要的参数列表：
     *     private final String providerId;
     *     private final ServiceProvider<A> serviceProvider;
     *     private final ApiAdapter<A> apiAdapter;
     * @param providerId  服务提供商ID
     * @param appId
     * @param appSecret
     */
    public QqConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QqServiceProvider(appId,appSecret), new QqAdapter());
    }
}
