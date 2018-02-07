package com.example.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 如果有ConnectionSignUp实例
 * JdbcUsersConnectionRepository中执行execute方法，将实例返回的ID及connection数据存在数据库
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp{

    @Override
    public String execute(Connection<?> connection) {
        /**
         * 根据社交用户信息默认创建用户并返回用户唯一标识
         */
        return "jch";
    }
}
