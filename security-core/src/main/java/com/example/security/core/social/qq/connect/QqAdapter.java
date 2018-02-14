package com.example.security.core.social.qq.connect;

import com.example.security.core.social.qq.api.Qq;
import com.example.security.core.social.qq.api.QqUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class QqAdapter implements ApiAdapter<Qq>{

    /**
     * 测试qq服务器是否可用
     * @param qq
     * @return
     */
    @Override
    public boolean test(Qq qq) {
        return true;
    }

    @Override
    public void setConnectionValues(Qq qq, ConnectionValues connectionValues) {

        QqUserInfo userInfo = qq.getUserInfo();

        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        // 主页
        connectionValues.setProfileUrl(null);
        // 用户身份
        connectionValues.setProviderUserId(userInfo.getOpenId());

    }


    @Override
    public UserProfile fetchUserProfile(Qq qq) {
        return null;
    }

    /**
     * 微博：发布消息更新微博
     * @param qq
     * @param message
     */
    @Override
    public void updateStatus(Qq qq, String message) {
        // do nothing
    }
}
