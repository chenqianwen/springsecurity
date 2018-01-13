package com.example.security.browser.authentication;

import com.example.security.browser.support.SimpleResponse;
import com.example.security.core.properties.LoginType;
import com.example.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败调用
 * SimpleUrlAuthenticationFailureHandler: spring默认的失败处理器
 */
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                 AuthenticationException e) throws IOException, ServletException {

        logger.info("登陆失败。。。。。");

        // 如果自定义的方式是json
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        }
        // 如果自定义的方式不是json，则调用spring默认的方式，跳转到作物页面上
        else {
            super.onAuthenticationFailure(request,response,e);
        }

    }
}
