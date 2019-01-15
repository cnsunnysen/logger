package com.mysteelsoft.logger.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * 通用业务异常封装
 *
 * @author shanyesen
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -3672735982747530689L;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ErrorCode errorCode;

    private String messageEx;

    public BaseException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseException(Throwable cause, ErrorCode errorCode, String format, Object... arguments) {
        super(cause);
        messageEx = MessageFormatter.arrayFormat(format, arguments).getMessage();
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        logger.debug("生成错误信息:{}", errorCode.getMessage());
    }

    public BaseException(ErrorCode errorCode, String format, Object... arguments) {
        messageEx = MessageFormatter.arrayFormat(format, arguments).getMessage();
        logger.debug("生成错误信息:{},{}", errorCode.getMessage(), messageEx);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public String getMessageEx() {
        return messageEx;
    }

    public void setMessageEx(String messageEx) {
        this.messageEx = messageEx;
    }
}
