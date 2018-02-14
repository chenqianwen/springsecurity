package com.example.dto;

import com.example.validator.MyConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class User {

    @MyConstraint(message = "测试--")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Date birthday;
}
