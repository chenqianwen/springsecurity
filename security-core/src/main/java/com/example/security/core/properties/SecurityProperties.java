package com.example.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "example.security")
public class SecurityProperties {

    @Getter
    @Setter
    private BrowserProperties browser = new BrowserProperties();

    @Getter
    @Setter
    private ValidateCodeProperties code = new ValidateCodeProperties();

    @Getter
    @Setter
    private SocialProperties socialProperties = new SocialProperties();

}
