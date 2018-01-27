package com.example.security.app.validate.code.impl;

import com.example.security.core.validate.code.ValidateCode;
import com.example.security.core.validate.code.ValidateCodeException;
import com.example.security.core.validate.code.ValidateCodeRepository;
import com.example.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * created by ygl on 2018/1/27
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     * redis中保存验证码
     * 设置30分钟超时时间，如果30分钟内没有验证过会清除掉该验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(buildKey(request,validateCodeType),code,30, TimeUnit.MINUTES);
    }

    /**
     * redis中获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        if (value == null) {
            return null;
        }
        return (ValidateCode)value;
    }

    /**
     * redis中移除验证码
     * @param request
     * @param validateCodeType
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildKey(request,validateCodeType));
    }

    /**
     * 生成key
     * @param request
     * @param validateCodeType
     * @return
     */
    private String buildKey(ServletWebRequest request,ValidateCodeType validateCodeType) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:"+validateCodeType.toString().toLowerCase()+":"+deviceId;
    }

}
