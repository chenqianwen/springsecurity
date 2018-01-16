package com.example.security.core.validate.code;

import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.image.ImageCodeGenerator;
import com.example.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.example.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统启动时，初始化这个bean前
     * 如果能找到imageValidateCodeGenerator名字的bean，那么不会初始化
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator () {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }
    /**
     * 系统启动时，初始化这个bean前
     * 如果能找到SmsCodeSender接口的实现，那么不会初始化
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender SmsCodeSender () {
        return new DefaultSmsCodeSender();
    }
}
