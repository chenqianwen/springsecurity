package com.example.security.core.properties;

import lombok.Data;

/**
 * 配置短信验证码的属性
 */
@Data
public class SmsCodeProperties {
    /**
     * 默认验证码数字个数
     */
    private int length = 4;
    /**
     * 默认验证码实效的时间秒数
     */
    private int expireIn = 60;
    /**
     * 需要校验验证码的url
     * 如；/user,/user/*
     */
    private String url;

}
