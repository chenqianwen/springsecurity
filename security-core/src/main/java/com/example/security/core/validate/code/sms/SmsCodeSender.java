package com.example.security.core.validate.code.sms;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public interface SmsCodeSender {

    /**
     * 发送验证码
     * @param mobile 手机号
     * @param code   验证码
     */
    void send(String mobile,String code);
}
