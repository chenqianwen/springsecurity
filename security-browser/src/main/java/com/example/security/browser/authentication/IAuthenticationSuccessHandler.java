package com.example.security.browser.authentication;

import com.example.security.core.properties.LoginType;
import com.example.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author： yl
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
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
