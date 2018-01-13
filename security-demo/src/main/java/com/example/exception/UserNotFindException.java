package com.example.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UserNotFindException extends RuntimeException {

    @Getter
    @Setter
    private Long id;

    public UserNotFindException(Long id) {
        super("user not find");
        this.id = id;
    }


}
