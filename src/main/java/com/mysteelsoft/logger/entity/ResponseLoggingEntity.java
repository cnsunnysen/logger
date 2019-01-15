package com.mysteelsoft.logger.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author shanyesen
 */
@Entity
@Table(name = "t_sys_log_response")
public class ResponseLoggingEntity extends BaseLoggingEntity {

    @ApiModelProperty("响应状态")
    private Integer status;

    @ApiModelProperty("响应首部")
    private String headers;

    @Override
    public BaseLoggingEntity autoConfig() {
        super.autoConfig();
        setLogType("http响应");
        return this;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
