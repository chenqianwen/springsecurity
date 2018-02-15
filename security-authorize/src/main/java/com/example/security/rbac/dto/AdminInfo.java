package com.example.security.rbac.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@Data
public class AdminInfo {

    private Long id;
    /**
     * 角色id
     */
    @NotBlank(message = "角色id不能为空")
    private Long roleId;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

}