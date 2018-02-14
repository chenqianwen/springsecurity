package com.example.security.core.properties;

import lombok.Data;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 图片验证码配置项
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
