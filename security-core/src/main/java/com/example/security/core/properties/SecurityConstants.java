package com.example.security.core.properties;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public interface SecurityConstants {

    /**
     * 默认的的处理码处理器 的url前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    /**
     * 当请求需要身份认证时，跳转到指定的登录页面Url
     * @see SecurityController
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
    /**
     * 默认的处理用户名密码登录请求url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的处理手机验证码登录请求url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";
    /**
     *默认的处理OPENID登录请求url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_OPENID = "/authentication/openid";
    /**
     * 默认登录页面
     * @see SecurityController
     */
    public static final String DEFAULT_LOGIN_PAGE_URL = "/default-sigIn.html";
    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或者 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     * session失效默认的跳转地址
     */
    public static final String DEFAULT_SESSION_INVALID_URL = "/session/invalid";
    /**
     * 默认的的社交登陆携带用户openId信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * 默认的的社交登陆携带服务提供商providerId信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

}
