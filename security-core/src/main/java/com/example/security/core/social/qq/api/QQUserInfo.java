package com.example.security.core.social.qq.api;

import lombok.Data;

/**
 * 获取qq的用户信息，对应的实体
 */
@Data
public class QQUserInfo {
    /**
     * openid是此网站上唯一对应用户身份的标识，
     * 网站可将此ID进行存储便于用户下次登录时辨识其身份，或将其与用户在网站上的原有账号进行绑定。
     */
    private String openId;
    /**
     * 返回码 :
     */
    private String ret;

    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;

    /**
     * 	用户在QQ空间的昵称。
     */
    private String nickname;

    /**
     *	大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;

    /**
     *	大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl_1;

    /**
     *	大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl_2;

    /**
     *	大小为40×40像素的QQ头像URL。
     */
    private String figureurl_qq_1;

    /**
     *	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
     */
    private String figureurl_qq_2;

    /**
     *	性别。 如果获取不到则默认返回"男"
     */
    private String gender;

    /**
     *	标识用户是否为黄钻用户（0：不是；1：是）。
     */
    private String is_yellow_vip;

    /**
     *	标识用户是否为黄钻用户（0：不是；1：是）
     */
    private String vip;

    /**
     *	黄钻等级
     */
    private String yellow_vip_level;

    /**
     *	黄钻等级
     */
    private String level;

    /**
     *	标识是否为年费黄钻用户（0：不是； 1：是）
     */
    private String is_yellow_year_vip;
}
