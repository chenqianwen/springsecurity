package com.example.security.core.social;

import com.example.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import lombok.Getter;
import lombok.Setter;
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

    @Setter
    @Getter
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

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
        if (socialAuthenticationFilterPostProcessor != null) {
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T)filter;
    }
}
