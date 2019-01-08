package com.mysteelsoft.logger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shanyesen
 */
@ApiModel("日志值对象")
public class LoggerVo implements Serializable {

    @ApiModelProperty("日志主键")
    private String id;

    @ApiModelProperty("操作用户Id")
    private String userId;

    @ApiModelProperty("操作用户Id")
    private String logModel;

    @ApiModelProperty("操作")
    private String logEvent;

    @ApiModelProperty("日志记录时间")
    private Date createDate = new Date();

    @ApiModelProperty("操作内容")
    private String admOptContent;

    @ApiModelProperty("目标方法名")
    private String targetMethod;

    @ApiModelProperty("目标方法所属类的类名")
    private String targetClass;

    @ApiModelProperty("错误信息")
    private String errorMsg;

    @ApiModelProperty("描述")
    private String infos;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogModel() {
        return logModel;
    }

    public void setLogModel(String logModel) {
        this.logModel = logModel;
    }

    public String getLogEvent() {
        return logEvent;
    }

    public void setLogEvent(String logEvent) {
        this.logEvent = logEvent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAdmOptContent() {
        return admOptContent;
    }

    public void setAdmOptContent(String admOptContent) {
        this.admOptContent = admOptContent;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }
}
