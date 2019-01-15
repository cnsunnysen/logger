package com.mysteelsoft.logger.repository;

import com.mysteelsoft.logger.vo.LoggingQueryVo;
import com.mysteelsoft.logger.vo.RequestLoggingVo;
import org.springframework.data.domain.Page;

/**
 * @author shanyesen
 */
public interface IRequestLoggingRepository {

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    Page<RequestLoggingVo> listData(LoggingQueryVo queryVo);
}
