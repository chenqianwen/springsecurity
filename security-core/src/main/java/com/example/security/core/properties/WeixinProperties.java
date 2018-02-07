package com.example.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class WeixinProperties extends SocialProperties {

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";
}

