package com.mysteelsoft.logger.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysteelsoft.logger.config.LogConstant;
import com.mysteelsoft.logger.entity.RequestLoggingEntity;
import com.mysteelsoft.logger.entity.ResponseLoggingEntity;
import com.mysteelsoft.logger.repository.RequestLoggingRepository;
import com.mysteelsoft.logger.repository.ResponseLoggingRepository;
import com.mysteelsoft.logger.service.LoggingService;
import com.mysteelsoft.logger.vo.LoggingQueryVo;
import com.mysteelsoft.logger.vo.RequestLoggingVo;
import com.mysteelsoft.logger.vo.RequestLoggingWhole;
import com.mysteelsoft.logger.vo.ResponseLoggingVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 日志处理服务实现
 *
 * @author shanyesen
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class LoggingServiceImpl implements LoggingService {

    public static Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RequestLoggingRepository requestLoggingRepository;

    @Autowired
    ResponseLoggingRepository responseLoggingRepository;

    @Override
    public Page<RequestLoggingVo> requestListData(LoggingQueryVo queryVo) {
        Page<RequestLoggingVo> requestLoggingVos = requestLoggingRepository.listData(queryVo);
        return requestLoggingVos;
    }

    @Override
    public Page<ResponseLoggingVo> responseListData(LoggingQueryVo queryVo) {
        return responseLoggingRepository.listData(queryVo);
    }

    @Override
    @Async
    public RequestLoggingEntity doRequestLogger(HttpServletRequest request, ContentCachingResponseWrapper response, String content) throws ServletException {
        RequestLoggingEntity logEntity = new RequestLoggingEntity();
        logEntity.autoConfig();
        logEntity.setLogGroupId(response.getHeader(LogConstant.GROUP_ID_HEADER_KEY));
        //请求方法
        logEntity.setMethod(request.getMethod());
        //请求路径
        String path = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
        logEntity.setPath(path);
        //请求首部
        logEntity.setHeaders(toRequestHeadersString(request));
        //请求IP
        String ip = getIpAddress(request);
        logEntity.setIp(ip);
        //内容
        String finalContent = content;
        if(content.length()>20000){
            StringBuilder sb = new StringBuilder("日志实体长度:");
            sb.append(content.length());
            sb.append(",截取前20000个字符,:");
            sb.append(content, 0, 20000);
            finalContent = sb.toString();
        }
        logEntity.setLogContent(finalContent);
        logger.info("2");
        //保存
        logEntity = requestLoggingRepository.save(logEntity);
        return logEntity;
    }

    @Override
//    @Async
    public ResponseLoggingEntity doResponseLogger(HttpServletRequest request, ContentCachingResponseWrapper response, String content, String groupId) {
        ResponseLoggingEntity logEntity = new ResponseLoggingEntity();
        logEntity.autoConfig();
        logEntity.setLogGroupId(groupId);
        String finalContent = content;
        if(content.length()>20000){
            StringBuilder sb = new StringBuilder("日志实体长度:");
            sb.append(content.length());
            sb.append(",截取前20000个字符,:");
            sb.append(content, 0, 20000);
            finalContent = sb.toString();
        }
        logEntity.setLogContent(finalContent);
        logEntity.setStatus(response.getStatusCode());
        String headers = response.getHeaderNames().stream().map(key -> response.getHeader(key)).collect(Collectors.joining(", ", "[", "]"));
        logEntity.setHeaders(headers);
        logEntity = responseLoggingRepository.save(logEntity);
        return logEntity;
    }

    @Override
    public RequestLoggingWhole requestDetail(LoggingQueryVo queryVo) {
        Assert.notNull(queryVo, "请求对象不为空");
        Assert.notNull(queryVo.getId(), "请求对象不为空");
        RequestLoggingWhole result = new RequestLoggingWhole();
        RequestLoggingVo requestLoggingVo = requestLoggingRepository.findVoById(queryVo.getId()).get();
        ResponseLoggingVo responseLoggingVo = responseLoggingRepository.findVoBylogGroupId(requestLoggingVo.getLogGroupId()).get();
        result.setRequestLoggingVo(requestLoggingVo);
        result.setResponseLoggingVo(responseLoggingVo);
        return result;
    }

    public String toRequestHeadersString(HttpServletRequest request) {
        String result = null;
        Map<String, String> resultMap = new LinkedHashMap<>();
        //请求实体类型
        resultMap.put("Content-Type", request.getContentType());
        //长度
        resultMap.put("content-length", request.getHeader("content-length"));
        //请求返回类型
        resultMap.put("accept", request.getHeader("accept"));
        //referer
        resultMap.put("referer", request.getHeader("referer"));
        //用户客户端
        resultMap.put("user-agent", request.getHeader("user-agent"));
        try {
            result = objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String toResponseHeadersString(ContentCachingResponseWrapper response) {
        String result = null;
        Map<String, String> resultMap = new LinkedHashMap<>();
        //响应实体类型
        resultMap.put("Content-Type", response.getContentType());
        //长度
        resultMap.put("content-length", response.getContentSize() + "");
        try {
            result = objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
