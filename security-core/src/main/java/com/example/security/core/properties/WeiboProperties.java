package com.example.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

@Data
public class WeiboProperties extends SocialProperties {
    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weibo。
     */
    private String providerId = "weibo";
}
