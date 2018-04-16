package com.thinkgem.jeesite.common.exception;

/**
 * 自定义验证异常
 */
public class ValidationException extends  RuntimeException {

    private int errorCode = 0;
    public ValidationException(String msg){
            super(msg);
    }

    public ValidationException(String msg,int errorCode){
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
