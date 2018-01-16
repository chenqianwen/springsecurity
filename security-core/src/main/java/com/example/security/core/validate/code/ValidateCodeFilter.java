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
    private AuthenticationFailureHandler iAuthenticationFailureHandler;

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
     * 例如：{"/authentication/form" : IMAGE}
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

        // 获取配置的 -- 需要验证图片验证码的url
        String imageUrl = securityProperties.getCode().getImage().getUrl();
        // 解析需要校验的 --配置的url
        addUrlToMap(imageUrl,ValidateCodeType.IMAGE);
        //  默认的需要校验图片验证码的URL 即：处理登录请求的的URL
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);

        // 需要验证短信验证码的url
        String smsUrl = securityProperties.getCode().getSms().getUrl();
        // 解析需要校验的 --配置的url
        addUrlToMap(smsUrl, ValidateCodeType.SMS);
        // 处理手机登录请求的的URL
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);

    }

    /**
     * 将系统中配置的需要校验验证码的url根据检验类型放入map中
     * @param configUrl
     * @param type
     */
    protected void addUrlToMap(String configUrl,ValidateCodeType type){
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
        // 若该请求需要验证，获取对应的验证码类型，否则为null
        ValidateCodeType type = getValidateCodeType(request);

        if (type != null) {
            logger.info("校验请求（"+request.getRequestURI()+")中的验证码，验证码类型："+ type);
            try {

                // 通过校验器的类型 获取对应的验证码处理器
                ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.findValidateCodeProcessor(type);

                // 校验 验证码
                validateCodeProcessor.validate(new ServletWebRequest(request,response));

                logger.info("校验码通过*******");
            }   catch (ValidateCodeException e) {
                /**
                 * 检验码 不通过会抛出 ValidateCodeException 异常
                 * 校验失败时，调用自定义的认证异常处理器：html请求则跳转到错误页面，json请求则返回json信息
                 */
                iAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
