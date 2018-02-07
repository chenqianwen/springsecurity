package com.example.security.core.social.weibo.connect;


import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 微信的access_token信息。与标准OAuth2协议不同，微信在获取access_token时会同时返回openId,并没有单独的通过accessToke换取openId的服务
 *
 * 所以在这里继承了标准AccessGrant，添加了uid字段，作为对微信access_token信息的封装。
 *
 */
public class WeiboAccessGrant extends AccessGrant {

    /**
     *
     */
    private static final long serialVersionUID = -7243374526633186782L;

    @Setter
    @Getter
    private String uid;

    public WeiboAccessGrant() {
        super("");
    }

    public WeiboAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

}
