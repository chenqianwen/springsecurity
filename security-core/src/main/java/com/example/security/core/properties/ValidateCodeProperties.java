package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 验证码配置
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
