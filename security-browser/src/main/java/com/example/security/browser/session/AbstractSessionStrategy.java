package com.example.security.browser.session;

import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 抽象session失效策略
 */
@Slf4j
public class AbstractSessionStrategy {

    private String urlEndWithHtml = ".html";

    /**
     * 跳转的url
     */
    private String destinationUrl;
    /**
     * 系统配置信息
     */
    private SecurityProperties securityProperties;
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    public AbstractSessionStrategy(SecurityProperties securityProperties) {
        String invalidSessionUrl = securityProperties.getBrowser().getSession().getSessionInvalidUrl();
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
        this.securityProperties = securityProperties;
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (createNewSession) {
            request.getSession();
        }

        String sourceUrl = request.getRequestURI();
        String targetUrl;
        /**
         * 如果请求是html结尾，则跳转到目标的html
         */
        if (StringUtils.endsWithIgnoreCase(sourceUrl, urlEndWithHtml)) {
            targetUrl = destinationUrl+".html";
            log.info("session失效,跳转到"+targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }else{
            Object result = buildResponseContent(request);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }

    }

    protected Object buildResponseContent(HttpServletRequest request) {
        String message = "session已失效";
        if (isConcurrency()) {
            message = message + "，有可能是并发登录导致的";
        }
        return new SimpleResponse(message);
    }

    /**
     * session失效是否是并发导致的
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    /**
     * Determines whether a new session should be created before redirecting (to
     * avoid possible looping issues where the same session ID is sent with the
     * redirected request). Alternatively, ensure that the configured URL does
     * not pass through the {@code SessionManagementFilter}.
     *
     * @param createNewSession
     *            defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
