package com.example.security.browser.authentication;

import com.example.security.core.support.SimpleResponse;
import com.example.security.core.enums.LoginType;
import com.example.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 认证失败调用
 * SimpleUrlAuthenticationFailureHandler: spring默认的失败处理器
 */
@Component
public class IAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

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
        // 如果自定义的方式不是json，则调用spring默认的方式，跳转到错误页面上
        else {
            super.onAuthenticationFailure(request,response,e);
        }

    }
}
