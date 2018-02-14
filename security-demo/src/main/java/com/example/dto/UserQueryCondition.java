package com.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class UserQueryCondition {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    private int age;
    private String size;
}
