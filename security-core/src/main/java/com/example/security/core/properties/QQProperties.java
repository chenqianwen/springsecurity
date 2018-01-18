package com.example.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;


public class QQProperties extends SocialProperties{

    @Setter
    @Getter
    private String providerId = "qq";


}
