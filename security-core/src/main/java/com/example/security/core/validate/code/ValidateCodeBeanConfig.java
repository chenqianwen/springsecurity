package com.example.security.core.validate.code;

import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.image.ImageCodeGenerator;
import com.example.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.example.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 图片验证码图片生成器
     * 系统启动时，初始化这个bean前
     * 如果能找到imageValidateCodeGenerator名字的bean，那么不会初始化
     * @return
     */
    @Bean("imageValidateCodeGenerator")
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator () {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }
    /**
     * 短信验证码发送器
     * 系统启动时，初始化这个bean前
     * 如果能找到SmsCodeSender接口的实现，那么不会初始化
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender () {
        return new DefaultSmsCodeSender();
    }
}
