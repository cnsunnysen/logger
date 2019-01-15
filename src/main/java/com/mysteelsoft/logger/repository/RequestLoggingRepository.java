package com.mysteelsoft.logger.repository;

import com.mysteelsoft.logger.entity.RequestLoggingEntity;
import com.mysteelsoft.logger.vo.RequestLoggingVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author shanyesen
 */
public interface RequestLoggingRepository extends JpaRepository<RequestLoggingEntity, String>, IRequestLoggingRepository {


    /**
     * 获取vo
     *
     * @param id
     * @return
     */
    @Query(value = "select new com.mysteelsoft.logger.vo.RequestLoggingVo(t1) from RequestLoggingEntity t1" +
            " where t1.id = :id ")
    Optional<RequestLoggingVo> findVoById(@Param("id") String id);
}
