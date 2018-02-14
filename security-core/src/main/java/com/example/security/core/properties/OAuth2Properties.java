package com.example.security.core.properties;

import lombok.Data;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class OAuth2Properties {

    /**
     *使用jwt时为token 签名的 默认秘钥
     */
    private String jwtSigningKey = "love";
    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

}
