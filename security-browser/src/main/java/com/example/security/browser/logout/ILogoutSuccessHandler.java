package com.example.security.browser.logout;

import com.example.security.core.support.SimpleResponse;
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
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 默认的退出成功处理器，如果设置了imooc.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
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
