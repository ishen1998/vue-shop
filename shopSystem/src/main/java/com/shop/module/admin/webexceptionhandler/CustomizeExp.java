package com.shop.module.admin.webexceptionhandler;

/**
 * @author zhoulanzhen
 * @date 2019/7/016 13:38
 */
public class CustomizeExp extends Exception {
    private String message;
    private long code;

    public CustomizeExp() {
        super();
    }

    public CustomizeExp(String message) {
        this.message = message;
    }

    public CustomizeExp(long code,String message) {
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public CustomizeExp(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
