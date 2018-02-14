package com.example.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 *  验证码生成器
 */
public interface ValidateCodeGenerator {

    /**
     * 生成代码
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
