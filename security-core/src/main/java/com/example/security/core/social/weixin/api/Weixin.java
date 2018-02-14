package com.example.security.core.social.weixin.api;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public interface Weixin {

    /**
     * 获取微信服务商用户细腻下
     * @param openId
     * @return
     */
    WeixinUserInfo getUserInfo(String openId);

}

