package com.example.security.core.validate.code;
import org.springframework.security.core.AuthenticationException;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
