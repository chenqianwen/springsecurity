package com.example.security.core.properties;

import lombok.Data;

/**
 * 配置图形验证码的属性
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{
    /**
     * 默认宽度
     */
    private int width = 67;
    /**
     * 默认高度
     */
    private int height = 23;
    /**
     * 默认设置4位长度的验证码
     */
    public ImageCodeProperties() {
        setLength(4);
    }
}
