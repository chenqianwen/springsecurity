package com.example.security.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {

    // 默认的登录页面
    private String loginPage = "/default-signIn.html";

    // 默认的注册页面
    private String signUpUrl = "/default-signUp.html";

    // 默认的返回方式
    private LoginType loginType = LoginType.JSON;

    // 默认的记住的时间秒数；1小时
    private int rememberMeSeconds = 3600;
}
