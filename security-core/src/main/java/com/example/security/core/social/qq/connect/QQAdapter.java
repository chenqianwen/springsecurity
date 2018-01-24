package com.example.security.core.social.qq.connect;

import com.example.security.core.social.qq.api.QQ;
import com.example.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class QQAdapter implements ApiAdapter<QQ>{

    /**
     * 测试qq服务器是否可用
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {

        QQUserInfo userInfo = qq.getUserInfo();

        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        // 主页
        connectionValues.setProfileUrl(null);
        // 用户身份
        connectionValues.setProviderUserId(userInfo.getOpenId());

    }


    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * 微博：发布消息更新微博
     * @param qq
     * @param message
     */
    @Override
    public void updateStatus(QQ qq, String message) {
        // do nothing
    }
}
