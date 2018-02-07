package com.example.security.core.validate.code.impl;

import com.example.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 *
 * 验证码的抽象父类：
 * 验证码处理器 -- 子类实例化时的bean名称格式如下即可：
 * ValidateCodeType类型枚举中的名称小写(写例如：sms) + CodeProcessor字符串 = smsCodeProcessor
 *
 * 验证码生成器 -- 子类实例化时的bean名称格式如下即可：
 * ValidateCodeType类型枚举中的名称小写(写例如：sms) + ValidateCodeGenerator字符串 = smsValidateCodeGenerator
 *
 * 综上：
 * 根据子类，即可通过子类名称获取对应的 验证码处理器 和 验证码生成器
 *
 * @param <C>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集所有的ValidateCodeGenerator 接口的实现
     * {实现类的名字 ： 类}
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 创建验证码
     * @param request
     * @throws Exception
     */
    @Override
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
     * 保存验证码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        //String sessionKey = SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase();
        //sessionStrategy.setAttribute(request,sessionKey, validateCode);
        // 往redis中无法序列化BufferedImage对象，把code存在redis即可
//        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
//        sessionStrategy.setAttribute(request, getSessionKey(), code);
        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
        validateCodeRepository.save(request,code,getValidateCodeType());
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
     * 校验 验证码
     * @param request
     */
    @Override
    public void validate(ServletWebRequest request) {
        // 获取验证码的类型
        ValidateCodeType codeType = getValidateCodeType();

        // 获取验证码 对应的form 的参数名称
        String paramNameOnValidate = codeType.getParamNameOnValidate();

        // 通过请求获取Session中的 具体的验证码实现类
        C codeInSession = (C) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            // 通过参数名称 获取请求中的 验证码的值
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), paramNameOnValidate);
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            validateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        /**
         *
         * 验证码正确时 移除session会话中存在的 关于验证码 键-值对
         *
         * 验证码错误是 抛出ValidateCodeException异常
         */
        validateCodeRepository.remove(request, codeType);
    }

}
