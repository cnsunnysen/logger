package com.mysteelsoft.logger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("请求日志详情")
public class RequestLoggingWhole implements Serializable {

    @ApiModelProperty("请求日志")
    RequestLoggingVo requestLoggingVo;

    @ApiModelProperty("响应日志")
    ResponseLoggingVo responseLoggingVo;

    public RequestLoggingVo getRequestLoggingVo() {
        return requestLoggingVo;
    }

    public void setRequestLoggingVo(RequestLoggingVo requestLoggingVo) {
        this.requestLoggingVo = requestLoggingVo;
    }

    public ResponseLoggingVo getResponseLoggingVo() {
        return responseLoggingVo;
    }

    public void setResponseLoggingVo(ResponseLoggingVo responseLoggingVo) {
        this.responseLoggingVo = responseLoggingVo;
    }
}
