package com.mysteelsoft.logger.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;


/**
 * 异常拦截
 *
 * @author shanyesen
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 非业务异常，根据异常类型封装成不同的异常封装体<br/>
     * 由于运行期因素所致
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper<Void> allExceptionHandler(Exception exception) {
        ResponseWrapper<Void> result = new ResponseWrapper<>();
        // 对于可以预见的异常类型进行简单的封装
        if (exception.getClass().equals(DataAccessException.class)) {
            result.failForCustom("911", "数据访问异常");
        } else if (exception.getClass().toString().equals(NullPointerException.class.toString())) {
            result.failForCustom("911", "空指针");
        } else if (exception.getClass().equals(IOException.class)) {
            result.failForCustom("911", "I/O流异常");
        } else if (exception.getClass().equals(ClassNotFoundException.class)) {
            result.failForCustom("911", "类未找到");
        } else if (exception.getClass().equals(ArithmeticException.class)) {
            result.failForCustom("911", "算术异常");
        } else if (exception.getClass().equals(ArrayIndexOutOfBoundsException.class)) {
            result.failForCustom("911", "数组越界异常");
        } else if (exception.getClass().equals(IllegalArgumentException.class)) {
            result.failForCustom("911", "非法参数异常");
        } else if (exception.getClass().equals(ClassCastException.class)) {
            result.failForCustom("911", "类型转换异常");
        } else if (exception.getClass().equals(SQLException.class)) {
            result.failForCustom("911", "数据库异常");
        } else {
            result.failForCustom("911", "其他异常");
        }
        logger.error("spring 拦截异常:exceptionClass:{}:{}", exception.getClass(), exception);
        return result;
    }

}