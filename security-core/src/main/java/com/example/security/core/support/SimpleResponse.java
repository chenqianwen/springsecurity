package com.example.security.core.support;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class SimpleResponse {

    public SimpleResponse (String content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
