package com.example.security.browser.validate.code.impl;

import com.example.security.core.validate.code.ValidateCode;
import com.example.security.core.validate.code.ValidateCodeRepository;
import com.example.security.core.validate.code.ValidateCodeType;
import lombok.experimental.var;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 验证码放入session时的前缀
     */
    public static String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * session中保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request,getSessionKey(validateCodeType),code);
    }

    /**
     * session中获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode)sessionStrategy.getAttribute(request,getSessionKey(validateCodeType));
    }

    /**
     * session中移除验证码
     * @param request
     * @param validateCodeType
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request,getSessionKey(validateCodeType));
    }

    /**
     * 获取构建验证码放入session时的key
     * @return
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
