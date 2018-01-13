package com.example.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置
 */
public class ValidateCodeProperties {

    @Getter
    @Setter
    private ImageCodeProperties image = new ImageCodeProperties();
}
