package com.example.security.core.validate.code.impl;

import com.example.security.core.validate.code.ValidateCodeBeanConfig;
import com.example.security.core.validate.code.ValidateCodeException;
import com.example.security.core.validate.code.ValidateCodeGenerator;
import com.example.security.core.validate.code.ValidateCodeProcessor;
import com.example.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<C> implements ValidateCodeProcessor {


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集所有的ValidateCodeGenerator 接口的实现
     * {实现类的名字 ： 类}
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 创建验证码
     * @param request
     * @throws Exception
     */
    public void create(ServletWebRequest request) throws Exception{
        // 生成代码
        C validateCode = generate(request);
        // 保存到session中
        save(request,validateCode);
        //  发送出去
        send(request,validateCode);
    }



    /**
     * 生成校验码
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        //String type = getProcessorType(request);
        //ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        //return (C)validateCodeGenerator.generate(request);
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 将验证码保存到session中
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        //String sessionKey = SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase();
        //sessionStrategy.setAttribute(request,sessionKey, validateCode);
        sessionStrategy.setAttribute(request, getSessionKey(), validateCode);
    }

    /**
     * 获取构建验证码放入session时的key
     * @return
     */
    private String getSessionKey() {
        return SESSION_KEY_PREFIX + getValidateCodeType().toString().toUpperCase();
    }

    /**
     *  根据调用的子类 获取校验码的类型
     *  截取子类的类名  在CodeProcessor字符串之前的一部分名称
     *  匹配ValidateCodeType枚举类型中的值SMS和IMAGE
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


    /**
     * 发送校验码，由子类实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    /**
     * 根据请求的url获取校验码的类型
     * 返回结果 image 或者 sms
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }


}
