package com.mysteelsoft.logger.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysteelsoft.logger.config.LogConstant;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志基类
 *
 * @author shanyesen
 */
@MappedSuperclass
public abstract class BaseLoggingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiModelProperty("日志主键")
    private String id;

    @Column(name = "log_type")
    @ApiModelProperty("日志类型:子类自行实现")
    private String logType;

    @Column(name = "log_platform")
    @ApiModelProperty("日志所属平台")
    private String logPlatform;

    @Column(name = "log_group_id")
    @ApiModelProperty("日志分组id")
    private String logGroupId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "log_create_datetime")
    @ApiModelProperty("日志创建时间")
    private Date logCreateDatetime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "log_content", columnDefinition = "TEXT")
    @ApiModelProperty("日志内容")
    private String logContent;

    public BaseLoggingEntity() {
    }

    /**
     * 自动配置属性<br/>
     * logGroupId、logCreateDateTime
     */
    public BaseLoggingEntity autoConfig() {
//        logGroupId = Thread.currentThread().getId()+"";
        logPlatform = LogConstant.LOG_PLATFORM_KEY;
        logCreateDatetime = new Date();
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogGroupId() {
        return logGroupId;
    }

    public void setLogGroupId(String logGroupId) {
        this.logGroupId = logGroupId;
    }

    public Date getLogCreateDatetime() {
        return logCreateDatetime;
    }

    public void setLogCreateDatetime(Date logCreateDatetime) {
        this.logCreateDatetime = logCreateDatetime;
    }

    public String getLogPlatform() {
        return logPlatform;
    }

    public void setLogPlatform(String logPlatform) {
        this.logPlatform = logPlatform;
    }
}
