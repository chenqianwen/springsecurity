package com.example.security.core.properties;

import com.example.security.core.constants.SecurityConstants;
import com.example.security.core.enums.LoginType;
import lombok.Data;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class BrowserProperties {

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
     *  默认的注册页面
     */
    private String signUpUrl = "/default-signUp.html";

    /**
     *  默认的退出页面 null
     */
    private String signOutUrl;

    /**
     *  默认的返回方式
     */
    private LoginType loginType = LoginType.REDIRECT;

    /**
     *  默认的记住的时间秒数；1小时
     */
    private int rememberMeSeconds = 3600;
}
