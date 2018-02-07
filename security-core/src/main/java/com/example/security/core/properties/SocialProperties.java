package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties  qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

    private WeiboProperties weibo = new WeiboProperties();
}
