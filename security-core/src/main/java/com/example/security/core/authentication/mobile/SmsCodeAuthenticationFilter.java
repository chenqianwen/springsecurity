package com.example.security.core.authentication.mobile;

import com.example.security.core.constants.SecurityConstants;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 短信登录过滤器 仿照UsernamePasswordAuthenticationFilter
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 请求中，接受手机号的参数名称
     */
    private String mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
    /**
     * 只接受post请求
     */
    private boolean postOnly = true;

    /**
     * 过滤器需要处理的 相匹配请求
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, HttpMethod.POST.name()));
    }

    /**
     * 认证流程
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);

            if (mobile == null) {
                mobile = "";
            }

            mobile = mobile.trim();

            /**
             * 实例化token
             */
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }


    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobileParameter parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
