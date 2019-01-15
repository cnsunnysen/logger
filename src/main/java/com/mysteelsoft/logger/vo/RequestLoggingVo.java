package com.mysteelsoft.logger.vo;

import com.mysteelsoft.logger.entity.RequestLoggingEntity;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

/**
 * @author shanyesen
 */
@ApiModel("请求日志")
public class RequestLoggingVo extends RequestLoggingEntity {

    public RequestLoggingVo() {
    }

    public RequestLoggingVo(RequestLoggingEntity entiry) {
        BeanUtils.copyProperties(entiry, this);
    }

}
