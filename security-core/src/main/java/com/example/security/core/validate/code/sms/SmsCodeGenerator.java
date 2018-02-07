package com.example.security.core.validate.code.sms;

import com.example.security.core.properties.ImageCodeProperties;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCode;
import com.example.security.core.validate.code.ValidateCodeGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 默认的短信验证码的生成器
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        // 过期时间秒数
        int expireIn = securityProperties.getCode().getSms().getExpireIn();
        // 验证码的长度
        int length = securityProperties.getCode().getSms().getLength();
        // 验证码
        String code = RandomStringUtils.randomNumeric(length);
        // 60秒有效
        return new ValidateCode(code,expireIn);
    }
}
