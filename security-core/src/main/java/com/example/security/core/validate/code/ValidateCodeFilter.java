package com.example.security.core.validate.code;

import com.example.security.core.properties.SecurityConstants;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.image.ImageCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
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
import java.util.Map;
import java.util.Set;

/**
 * OncePerRequestFilter 工具类：过滤器只会被调用一次
 * InitializingBean: 是为了在在其他参数组装完毕后，初始化urls的值
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

    /**
     * 验证码校验失败的处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String,ValidateCodeType> urlMap = new HashedMap();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化urs的值
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 需要验证图片验证码的url
        String imageUrl = securityProperties.getCode().getImage().getUrl();
        // 需要验证短信验证码的url
        String smsUrl = securityProperties.getCode().getSms().getUrl();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlTOMap(imageUrl,ValidateCodeType.IMAGE);

    }

    /**
     * 将系统中配置的需要校验验证码的url根据检验类型放入map中
     * @param configUrl
     * @param type
     */
    protected void addUrlTOMap(String configUrl,ValidateCodeType type){
        if (StringUtils.isNotBlank(configUrl)) {
            String[] urs = StringUtils.splitByWholeSeparatorPreserveAllTokens(configUrl, ",");
            if (urs != null) {
                for (String url : urs) {
                    urlMap.put(url,type);
                }
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(request);

        if (type != null) {
            logger.info("校验请求（"+request.getRequestURI()+")中的验证码，验证码类型："+ type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request,response));
                logger.info("校验码通过");
            }   catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {
        // 对应的key
        String sessionKey = ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE";
        // 存在session中的验证码
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(request, sessionKey);
        // 在请求中的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

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
