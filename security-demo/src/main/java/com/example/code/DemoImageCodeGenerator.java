package com.example.code;

import com.example.security.core.validate.code.image.ImageCode;
import com.example.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 自定义图形验证码的生成
 * @Component("imageCodeGenerator") 名称配置使得 ValidateCodeBeanConfig 配置类中
 * @ConditionalOnMissingBean(name = "imageCodeGenerator")不会初始化默认的生成器bean
 *
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator{

    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("自定义的图形验证码-------------------------------------");
        return null;
    }
}
