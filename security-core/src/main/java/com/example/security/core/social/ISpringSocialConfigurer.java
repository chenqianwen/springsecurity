package com.example.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class ISpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * 设置SocialAuthenticationFilter拦截器需要拦截的请求url
     */
    private String filterProcessesUrl;

    public ISpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * @param object  过滤器filter
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter =  (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T)filter;
    }
}
