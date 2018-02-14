package com.example.web.controller;

import com.example.exception.UserNotFindException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserNotFindException (UserNotFindException ex) {
        Map<String,Object> result = new HashMap<>(2);
        result.put("id",ex.getId());
        result.put("message",ex.getMessage());
        return result;
    }
}
