package com.mysteelsoft.logger.other;

import java.io.Serializable;

/**
 * @author shanyesen
 */
public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;

    private T data;

    private String errorcode;

    private String errormessage;


    public ResponseWrapper<T> success() {
        this.status = 0;
        return this;
    }

    public ResponseWrapper<T> success(T data) {
        this.data = data;
        return success();
    }

    public ResponseWrapper<T> fail() {
        this.status = 1;
        return this;
    }

    public ResponseWrapper<T> fail(ErrorCode re) {
        this.status = 1;
        this.errorcode = re.getCode();
        this.errormessage = re.getMessage();
        return this;
    }

    public ResponseWrapper<T> fail(BaseException bex) {
        this.status = 1;
        ErrorCode re = bex.getErrorCode();
        this.errorcode = re.getCode();
        if (bex.getMessageEx() != null) {
            this.errormessage = re.getMessage() + "," + bex.getMessageEx();
        } else {
            this.errormessage = re.getMessage();
        }
        return this;
    }

    public ResponseWrapper<T> fail(ErrorCode re, String messageEx) {
        this.status = 1;
        this.errorcode = re.getCode();
        this.errormessage = re.getMessage() + "," + messageEx;
        return this;
    }

    public ResponseWrapper<T> failForCustom(String code, String message) {
        this.status = 1;
        this.errorcode = code;
        this.errormessage = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }


}
