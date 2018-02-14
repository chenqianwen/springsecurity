package com.example.security.core.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Slf4j
public class QqOAuth2Template extends OAuth2Template {

    public QqOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 获取access_token
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        log.info("获取accessToken的响应:" + responseStr);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        String accessToken = StringUtils.substringAfter(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfter(items[1], "="));
        String refreshToken = StringUtils.substringAfter(items[2], "=");

        return new AccessGrant(accessToken,null,refreshToken,expiresIn);
    }

    /**
     *
     * 父类createRestTemplate方法中只有三个转换器，
     *   converters.add(new FormHttpMessageConverter());    负责读取form提交的数据
     *   converters.add(new FormMapHttpMessageConverter());
     *   converters.add(new MappingJackson2HttpMessageConverter());  负责读取和写入json格式的数据
     *   处理qq返回的text/html 类型数据:默认的是ISO-8859-1
     *   需要再加入一个将请求信息转换为一个对象 转换器
     *   StringHttpMessageConverter 负责读取字符串格式的数据和写出二进制格式的数据；
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
