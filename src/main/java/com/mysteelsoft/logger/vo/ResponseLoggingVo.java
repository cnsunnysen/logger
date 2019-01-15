package com.mysteelsoft.logger.vo;

import com.mysteelsoft.logger.entity.ResponseLoggingEntity;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

/**
 * @author shanyesen
 */
@ApiModel("响应日志")
public class ResponseLoggingVo extends ResponseLoggingEntity {

    public ResponseLoggingVo() {
    }

    public ResponseLoggingVo(ResponseLoggingEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
