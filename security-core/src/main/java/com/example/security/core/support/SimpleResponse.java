package com.example.security.core.support;

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