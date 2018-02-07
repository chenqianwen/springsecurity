package com.example.security.core.validate.code;


import org.springframework.web.context.request.ServletWebRequest;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 校验码处理器，封装不同校验码的处理逻辑
 */
public interface ValidateCodeProcessor {

    /**
     * 创建校验码
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);
}
