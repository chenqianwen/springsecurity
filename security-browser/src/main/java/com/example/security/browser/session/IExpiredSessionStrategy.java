package com.example.security.browser.session;

import com.example.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 并发登录导致session失效时，默认的处理策略
 */
public class IExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public IExpiredSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    /**
     * session失效事件
     * @param event
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }

}