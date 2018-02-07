package com.example.security.core.properties;

import lombok.Data;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class OAuth2Properties {

    private String jwtSigningKey = "love";

    private OAuth2ClientProperties[] clients = {};

}
