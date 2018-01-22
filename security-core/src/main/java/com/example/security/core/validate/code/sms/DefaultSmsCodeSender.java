package com.example.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender{

    @Override
    public void send(String mobile, String code) {
        log.info("向手机："+mobile+"**********************************发送短信验证码:"+code+"**********************************");
    }
}
