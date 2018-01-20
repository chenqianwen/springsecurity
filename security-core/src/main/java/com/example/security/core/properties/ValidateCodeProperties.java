package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
