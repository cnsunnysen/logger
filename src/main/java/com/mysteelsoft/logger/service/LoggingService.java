package com.mysteelsoft.logger.service;

import com.mysteelsoft.logger.entity.RequestLoggingEntity;
import com.mysteelsoft.logger.entity.ResponseLoggingEntity;
import com.mysteelsoft.logger.vo.LoggingQueryVo;
import com.mysteelsoft.logger.vo.RequestLoggingVo;
import com.mysteelsoft.logger.vo.RequestLoggingWhole;
import com.mysteelsoft.logger.vo.ResponseLoggingVo;
import org.springframework.data.domain.Page;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 日志处理服务
 *
 * @author shanyesen
 */
public interface LoggingService {

    /**
     * 请求日志列表
     *
     * @param queryVo
     * @return
     */
    Page<RequestLoggingVo> requestListData(LoggingQueryVo queryVo);

    /**
     * 响应日志列表
     *
     * @param queryVo
     * @return
     */
    Page<ResponseLoggingVo> responseListData(LoggingQueryVo queryVo);

    /**
     * 处理http请求日志
     *
     * @param request
     * @param response
     * @param content
     * @return
     * @throws ServletException
     */
    RequestLoggingEntity doRequestLogger(HttpServletRequest request, ContentCachingResponseWrapper response, String content) throws ServletException;


    /**
     * 处理http响应日志
     *
     * @param request
     * @param response
     * @param content
     * @param groupId
     * @return
     */
    ResponseLoggingEntity doResponseLogger(HttpServletRequest request, ContentCachingResponseWrapper response, String content, String groupId);

    /**
     * 请求日志详情
     *
     * @param queryVo
     * @return
     */
    RequestLoggingWhole requestDetail(LoggingQueryVo queryVo);
}
