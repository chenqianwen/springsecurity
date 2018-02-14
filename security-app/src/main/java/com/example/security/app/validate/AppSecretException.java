package com.example.security.app.validate;

/**
 * @author： ygl
 * @date： 2018/2/14
 * @Description：
 */
public class AppSecretException extends RuntimeException {

    private static final long serialVersionUID = -1629364510827838114L;

    public AppSecretException(String msg){
        super(msg);
    }

}
