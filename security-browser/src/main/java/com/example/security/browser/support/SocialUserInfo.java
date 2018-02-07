package com.example.security.browser.support;

import lombok.Data;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
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
