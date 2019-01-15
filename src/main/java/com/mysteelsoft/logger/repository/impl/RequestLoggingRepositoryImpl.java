package com.mysteelsoft.logger.repository.impl;

import com.esteel.common.dao.PageQuery;
import com.mysteelsoft.logger.repository.IRequestLoggingRepository;
import com.mysteelsoft.logger.vo.LoggingQueryVo;
import com.mysteelsoft.logger.vo.RequestLoggingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shanyesen
 */
public class RequestLoggingRepositoryImpl implements IRequestLoggingRepository {

    @Autowired
    PageQuery pageQuery;

    @Override
    public Page<RequestLoggingVo> listData(LoggingQueryVo queryVo) {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select t1.* From t_sys_log_request t1 ");
        sql.append(" where 1=1  ");
        List<Object> args = new ArrayList<>();

        if (queryVo.getTimeBegin() != null) {
            sql.append(" and t1.log_create_datetime >= ?");
            args.add(queryVo.getTimeBegin());
        }
        if (queryVo.getTimeEnd() != null) {
            sql.append(" and t1.log_create_datetime <= ?");
            args.add(queryVo.getTimeEnd());
        }
        sql.append(" order by t1.log_create_datetime desc ");
        return pageQuery.query(sql.toString(), args, queryVo, RequestLoggingVo.class);
    }
}
