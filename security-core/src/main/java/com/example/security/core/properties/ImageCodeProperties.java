package com.example.security.core.properties;

import lombok.Data;

/**
 * 配置图形验证码的属性
 */
@Data
public class ImageCodeProperties {
    /**
     * 默认宽度
     */
    private int width = 67;
    /**
     * 默认高度
     */
    private int height = 23;
    /**
     * 默认验证码数字个数
     */
    private int length = 4;
    /**
     * 默认验证码实效的时间秒数
     */
    private int expireIn = 60;
    /**
     * 需要验证图片验证码的url
     * 如；/user,/user/*
     */
    private String url;
}
