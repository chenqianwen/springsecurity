package com.example.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author： ygl
 * @date： 2018/2/8-11:54
 * @Description：
 * SocialAuthenticationFilter后处理器，用于在不同环境下个性化社交登录的配置
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     * 处理社交登录
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
