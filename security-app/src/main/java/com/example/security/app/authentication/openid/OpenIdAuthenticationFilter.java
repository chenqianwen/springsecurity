package com.example.security.app.authentication.openid;

import com.example.security.core.constants.SecurityConstants;
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
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;

    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;

    /**
     * 只接受post请求
     */
    private boolean postOnly = true;

    /**
     * 过滤器需要处理的 相匹配请求
     */
    public OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, "POST"));
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
            String openId = this.obtainOpenId(request);
            String proverId = this.obtainProverId(request);

            if (openId == null) {
                openId = "";
            }
            if (proverId == null) {
                proverId = "";
            }

            openId = openId.trim();
            proverId = proverId.trim();

            /**
             * 实例化token
             */
            OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId,proverId);

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(this.openIdParameter);
    }

    protected String obtainProverId(HttpServletRequest request) {
        return request.getParameter(this.providerIdParameter);
    }


    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "openIdParameter parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        Assert.hasText(providerIdParameter, "providerIdParameter parameter must not be empty or null");
        this.providerIdParameter = providerIdParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
