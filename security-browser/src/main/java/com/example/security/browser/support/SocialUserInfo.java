package com.example.security.browser.support;

import lombok.Data;

@Data
public class SocialUserInfo {

    /**
     * 哪个第三方用户 qq,wx
     */
    private String providerId;

    /**
     * openid
     */
    private String providerUserId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImg;
}
