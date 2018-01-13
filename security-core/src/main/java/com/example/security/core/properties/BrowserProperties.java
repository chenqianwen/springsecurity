package com.example.security.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {

    // 默认的登录页面
    private String loginPage = "/signIn.html";

    // 默认的返回方式
    private LoginType loginType = LoginType.JSON;


}
