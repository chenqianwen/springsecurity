package com.example.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class QQProperties extends SocialProperties{

    private String providerId = "qq";

}
