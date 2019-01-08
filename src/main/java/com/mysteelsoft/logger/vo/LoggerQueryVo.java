package com.mysteelsoft.logger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author shanyesen
 */
@ApiModel("日志查询值对象")
public class LoggerQueryVo implements Serializable {

    @ApiModelProperty("时间范围")
    private String timeRange;


    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
}
