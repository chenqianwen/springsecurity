package com.example.security.browser.authentication;

import com.example.security.core.enums.LoginType;
import com.example.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 *  认证成功后调用
 *  SavedRequestAwareAuthenticationSuccessHandler: spring默认的成功处理器
 */
@Component
public class IAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                  Authentication authentication) throws IOException, ServletException {

        logger.info("登陆成功。。。。。");

        // 如果自定义的登录方式是json
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }
        // 如果自定义的登录方式不是json，则跳转
        else {
            /**
             * 如果设置了security.browser.singInSuccessUrl，总是跳到设置的地址上
             * 如果没设置，则尝试跳转到登录之前访问的地址上，如果登录前访问地址为空，则跳到网站根路径上
             * SavedRequestAwareAuthenticationSuccessHandler中处理
             */
            if (StringUtils.isNotBlank(securityProperties.getBrowser().getSingInSuccessUrl())) {
                requestCache.removeRequest(request, response);
                setAlwaysUseDefaultTargetUrl(true);
                setDefaultTargetUrl(securityProperties.getBrowser().getSingInSuccessUrl());
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
