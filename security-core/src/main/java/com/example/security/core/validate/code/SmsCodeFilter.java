package com.example.security.core.validate.code;

import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.image.ImageCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * OncePerRequestFilter 工具类：过滤器只会被调用一次
 * InitializingBean: 是为了在在其他参数组装完毕后，初始化urls的值
 */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean{

    @Setter
    @Getter
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Setter
    @Getter
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 需要拦截的url
     */
    @Setter
    @Getter
    private Set<String> urls = new HashSet<>();

    @Setter
    @Getter
    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化urs的值
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String url = securityProperties.getCode().getSms().getUrl();
        // 配置的需要验证图形验证码的url
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
        if (configUrls != null) {
            for (String configUrl : configUrls) {
                urls.add(configUrl);
            }
        }
        // 登录的url
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果请求则校验
        boolean action = false;
        // 如果请求的uri 匹配到 配置的url 则需要验证图形码
        for (String url : urls) {
            if (pathMatcher.match(url,request.getRequestURI())) {
                action =true;
            }
        }
        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request,response,exception);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {
        // 对应的key
        String sessionKey = ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS";
        // 存在session中的验证码
        ValidateCode codeInSession = (ValidateCode)sessionStrategy.getAttribute(request, sessionKey);
        // 在请求中的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(),codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request,sessionKey);
    }
}
