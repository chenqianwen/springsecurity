package com.example.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *  验证码生成器
 */
public interface ValidateCodeGenerator {

    ImageCode generate(ServletWebRequest request);
}
