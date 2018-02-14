package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 社交登录配置项
 */
@Data
public class SocialProperties {

    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";

    private QqProperties  qq = new QqProperties();

    private WeixinProperties weixin = new WeixinProperties();

    private WeiboProperties weibo = new WeiboProperties();
}
