package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SocialProperties {

    private QQProperties  qq = new QQProperties();
}
