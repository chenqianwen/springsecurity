package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

@Data
public class QQProperties extends SocialProperties{

    private String providerId = "qq";

    private String filterProcessesUrl = "/auth";


}
