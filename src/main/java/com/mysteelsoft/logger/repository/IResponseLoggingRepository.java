package com.mysteelsoft.logger.repository;

import com.mysteelsoft.logger.vo.LoggingQueryVo;
import com.mysteelsoft.logger.vo.ResponseLoggingVo;
import org.springframework.data.domain.Page;

/**
 * @author shanyesen
 */
public interface IResponseLoggingRepository {

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    Page<ResponseLoggingVo> listData(LoggingQueryVo queryVo);

}
