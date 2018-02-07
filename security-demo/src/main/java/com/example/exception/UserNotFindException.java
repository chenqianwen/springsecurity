package com.example.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class UserNotFindException extends RuntimeException {

    @Getter
    @Setter
    private Long id;

    public UserNotFindException(Long id) {
        super("user not find");
        this.id = id;
    }


}
