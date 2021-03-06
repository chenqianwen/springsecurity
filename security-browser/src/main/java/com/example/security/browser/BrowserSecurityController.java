package com.example.security.browser;

import com.example.security.core.social.SocialController;
import com.example.security.core.social.support.SocialUserInfo;
import com.example.security.core.support.SimpleResponse;
import com.example.security.core.constants.SecurityConstants;
import com.example.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 *  浏览器环境下与安全相关的服务处理请求
 */
@RestController
@Slf4j
public class BrowserSecurityController extends SocialController {

    private String urlEndWithHtml = ".html";

    /**
     * HttpSessionRequestCache ：当前请求缓存到session中
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 跳转类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;



    /**
     * 需要身份验证时，跳转到这里
     * 如果是
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从session中取原始的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            // 引发跳转的请求的url
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求的url:"+redirectUrl);
            // 如果是访问html页面，则跳转到首页
            if (StringUtils.endsWithIgnoreCase(redirectUrl,urlEndWithHtml)) {
                String loginPage = securityProperties.getBrowser().getSignInPage();
                redirectStrategy.sendRedirect(request,response,loginPage);
            }
        }
        return new SimpleResponse("访问的服务需要身份验证，请先登录！");
    }

    /**
     * 获取社交登录的用户信息
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return buildSocialUserInfo(connectionFromSession);
    }

}
