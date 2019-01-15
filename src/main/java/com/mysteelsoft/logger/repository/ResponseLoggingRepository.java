package com.mysteelsoft.logger.repository;

import com.mysteelsoft.logger.entity.ResponseLoggingEntity;
import com.mysteelsoft.logger.vo.ResponseLoggingVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author shanyesen
 */
public interface ResponseLoggingRepository extends JpaRepository<ResponseLoggingEntity, String>, IResponseLoggingRepository {

    /**
     * 获取vo
     *
     * @param id
     * @return
     */
    @Query(value = "select new com.mysteelsoft.logger.vo.ResponseLoggingVo(t1) from ResponseLoggingEntity t1 " +
            " where t1.id = :id ")
    Optional<ResponseLoggingVo> findVoById(@Param("id") String id);

    /**
     * @param logGroupId
     * @return
     */
    @Query(value = "select new com.mysteelsoft.logger.vo.ResponseLoggingVo(t1) from ResponseLoggingEntity t1 " +
            " where t1.logGroupId = :logGroupId ")
    Optional<ResponseLoggingVo> findVoBylogGroupId(@Param("logGroupId") String logGroupId);
}
