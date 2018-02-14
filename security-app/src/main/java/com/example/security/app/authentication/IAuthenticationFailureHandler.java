package com.example.security.app.authentication;

import com.example.security.core.enums.LoginType;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 认证失败调用
 * SimpleUrlAuthenticationFailureHandler: spring默认的失败处理器
 */
@Component
@Slf4j
public class IAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                 AuthenticationException e) throws IOException, ServletException {

        log.info("登陆失败。。。。。");

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
    }
}
