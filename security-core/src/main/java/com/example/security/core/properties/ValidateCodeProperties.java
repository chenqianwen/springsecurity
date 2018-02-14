package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 验证码配置
 */
@Data
public class ValidateCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();
    /**
     * 短信验证码配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();
}
