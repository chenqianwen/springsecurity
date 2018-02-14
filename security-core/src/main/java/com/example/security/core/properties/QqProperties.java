package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * QQ登录配置项
 */
@Data
public class QqProperties extends SocialProperties{

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 qq。
     */
    private String providerId = "qq";

}
