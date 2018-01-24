package com.example.security.browser.logout;

import com.example.security.browser.support.SimpleResponse;
import com.example.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的自定义退出成功处理器
 */
@Slf4j
public class ILogoutSuccessHandler implements LogoutSuccessHandler {

    private String signOutUrl;

    public ILogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String msg = "退出系统成功";
        log.info(msg);

        // 如果没有配置退出页面：返回JSON. 如果配置了退出页面，返回到html
        if (StringUtils.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(msg)));
        } else {
            response.sendRedirect(signOutUrl);
        }

    }
}
