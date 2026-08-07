package com.norm.timemall.app.base.exception;


/**
 * 返回错误码
 *
 * @author yanpanyi
 * @date 2019/3/20
 */
public class QuickMessageException extends RuntimeException {

    private String message;

    public QuickMessageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
