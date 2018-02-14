package com.example.security.core.social.weibo.api;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public interface Weibo {

    /**
     * 获取微博服务商的用户信息
     * @param uid
     * @return
     */
    WeiboUserInfo getUserInfo(String uid);
}

