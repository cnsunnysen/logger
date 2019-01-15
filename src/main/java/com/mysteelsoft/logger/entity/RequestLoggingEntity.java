package com.mysteelsoft.logger.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author shanyesen
 */
@Entity
@Table(name = "t_sys_log_request")
public class RequestLoggingEntity extends BaseLoggingEntity {

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("请求首部")
    private String headers;

    @ApiModelProperty("请求ip")
    private String ip;

    @Override
    public BaseLoggingEntity autoConfig() {
        super.autoConfig();
        setLogType("http请求");
        return this;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
