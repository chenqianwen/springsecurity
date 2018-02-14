package com.example.web.config;

import com.example.web.filter.TimeFilter;
import com.example.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 可以不用@Component注解手动添加过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);
        //  对于哪些url有效
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }
}
