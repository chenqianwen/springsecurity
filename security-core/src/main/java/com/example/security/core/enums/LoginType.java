package com.example.security.core.enums;

import com.alibaba.fastjson.JSON;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 认证成功后的响应方式
 */
public enum LoginType {

    /**
     * 登录类型，重定向
     */
    REDIRECT,
    /**
     * 登录类型,json格式
     */
    JSON

}
