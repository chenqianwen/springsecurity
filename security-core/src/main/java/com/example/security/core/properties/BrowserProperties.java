package com.example.security.core.properties;

import com.example.security.core.constants.SecurityConstants;
import com.example.security.core.enums.LoginType;
import lombok.Data;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 浏览器环境配置项
 */
@Data
public class BrowserProperties {

    /**
     * session管理配置项
     */
    private SessionProperties session = new SessionProperties();
    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
     */
    private String signInPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     *
     * 只在signInResponseType为REDIRECT时生效
     */
    private String singInSuccessUrl;
    /**
     *  社交登录，如果需要用户注册，默认跳转的页面
     */
    private String signUpUrl = "/default-signUp.html";
    /**
     *  默认退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
     */
    private String signOutUrl;
    /**
     *  默认的登录响应的方式，默认是json
     */
    private LoginType loginType = LoginType.JSON;
    /**
     *  默认'记住我'功能的有效时间，1小时
     */
    private int rememberMeSeconds = 3600;
}
