package com.example.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class IExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {


    public IExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
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

    /* (non-Javadoc)
     * @see com.imooc.security.browser.session.AbstractSessionStrategy#isConcurrency()
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }

}