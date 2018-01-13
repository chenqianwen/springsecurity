package com.example.dto;

import lombok.Data;

@Data
public class UserQueryCondition {
    private String username;
    private String password;
    private int age;
}
