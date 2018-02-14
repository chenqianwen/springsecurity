package com.example.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 验证码信息封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCode implements Serializable{
    /**
     *  随机数
     */
    private String code;
    /**
     *  过期时间
     */
    private LocalDateTime expireTime;

    /**
     * @param code
     * @param expireIn 多少秒内过期
     */
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
