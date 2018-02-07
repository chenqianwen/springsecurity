package com.example.dto;

import lombok.Data;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Data
public class UserQueryCondition {
    private String username;
    private String password;
    private int age;
}
