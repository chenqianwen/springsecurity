package com.example.security.core.social.weibo.connect;

import com.example.security.core.social.weibo.api.Weibo;
import com.example.security.core.social.weibo.api.WeiboUserInfo;
import com.example.security.core.social.weixin.api.Weixin;
import com.example.security.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 微博 api适配器，将微博 api的数据模型转为spring social的标准模型。
 *  由于uid不同，会声明多实例对象
 */
public class WeiboAdapter implements ApiAdapter<Weibo> {

    private String uid;

    public WeiboAdapter() {}

    public WeiboAdapter(String uid){
        this.uid = uid;
    }

    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(Weibo api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(Weibo api, ConnectionValues values) {
        WeiboUserInfo profile = api.getUserInfo(uid);
        values.setProviderUserId(profile.getIdstr());
        values.setDisplayName(profile.getScreen_name());
        values.setImageUrl(profile.getProfile_image_url());
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(Weibo api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(Weibo api, String message) {
        //do nothing
    }

}
