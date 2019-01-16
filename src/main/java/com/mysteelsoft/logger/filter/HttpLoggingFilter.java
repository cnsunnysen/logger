package com.mysteelsoft.logger.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysteelsoft.logger.config.LogConstant;
import com.mysteelsoft.logger.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 日志过滤器
 *
 * @author shanyesen
 */
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LoggingService loggingService;

    /**
     * 父类 logger使用commons.logging 并且final
     */
    public static Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);

    private boolean shouldLog = true;

    private String ignorePatternsStr;

    private List<String> ignorePatterns;

    private static final PathMatcher logIgnoreMatcher = new AntPathMatcher();

    /**
     * 是否对本次请求不做处理
     *
     * @param request
     * @return
     */
    protected boolean notIgnoreRequest(HttpServletRequest request) {
        //只处理 json和表单提交
        if (!StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE) && !StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            return false;
        }
        String requestPath = getRequestPath(request);
        if (ignorePatterns != null) {
            for (String ignorePattern : ignorePatterns) {
                if (logIgnoreMatcher.match(ignorePattern, requestPath)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        HttpServletResponse responseToUse = response;
        boolean isLogRequest = false;
        String groupId = null;
        if (shouldLog && isFirstRequest) {
            if (notIgnoreRequest(request)) {
                isLogRequest = true;
                if(StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_FORM_URLENCODED_VALUE)){
                    requestToUse = new ContentCachingRequestWrapper(request);
                }else{
                    requestToUse = new RequestLoggingWrapper(request);
                }
                responseToUse = new ContentCachingResponseWrapper(response);
                doRequestLogger(requestToUse, (ContentCachingResponseWrapper) responseToUse);
            } else {
                log.debug("HttpLoggingFilter ignore path:{}", getRequestPath(requestToUse));
            }
        }
        try {
            //执行后续filter以及内部程序
            filterChain.doFilter(requestToUse, responseToUse);
            if (isLogRequest) {
                doResponseLogger((RequestLoggingWrapper) requestToUse, (ContentCachingResponseWrapper) responseToUse, groupId);
            }
        } finally {

        }
    }

    /**
     * 请求日志处理
     *
     * @param requestToUse
     * @throws UnsupportedEncodingException
     */
    private void doRequestLogger(HttpServletRequest requestToUse, ContentCachingResponseWrapper responseToUse) throws UnsupportedEncodingException, ServletException {
        if (requestToUse == null) {
            return;
        }
        byte[] contentAsByteArray = null;
        if(requestToUse instanceof ContentCachingRequestWrapper ){
            contentAsByteArray = ((ContentCachingRequestWrapper) requestToUse).getContentAsByteArray();
        }else{
            contentAsByteArray = ((RequestLoggingWrapper) requestToUse).getOnceBody();
        }
        if (contentAsByteArray == null) {
            return;
        }
        try {
            String groupId = UUID.randomUUID().toString().replaceAll("-", "");
            responseToUse.setHeader(LogConstant.GROUP_ID_HEADER_KEY, groupId);
            char[] chars = getChars(contentAsByteArray, Charset.forName(requestToUse.getCharacterEncoding()));
            loggingService.doRequestLogger(requestToUse, responseToUse, chars);
        } catch (Exception e) {
            log.error("请求日志自身问题不向外抛出", e);
        }

    }

    /**
     * 响应日志处理
     *
     * @param responseToUse
     * @throws IOException
     */
    private void doResponseLogger(RequestLoggingWrapper requestToUse, ContentCachingResponseWrapper responseToUse, String groupId) throws IOException {
        if (responseToUse == null) {
            return;
        }
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(responseToUse.getContentInputStream());
            char[] chars = getChars(bytes, Charset.forName(requestToUse.getCharacterEncoding()));
//            String str = new String(bytes, Charset.forName(requestToUse.getCharacterEncoding()));
//            JsonNode jsonNode = objectMapper.readTree(str);
            responseToUse.copyBodyToResponse();
            log.info("1.groupId:{}", responseToUse.getHeader(LogConstant.GROUP_ID_HEADER_KEY));
            loggingService.doResponseLogger(requestToUse, responseToUse, chars, groupId);
        } catch (Exception e) {
            log.error("响应日志自身问题不向外抛出", e);
        } finally {

        }
    }

    protected String getRequestPath(HttpServletRequest request) {
        String path = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            path += pathInfo;
        }
        return path;
    }

    public boolean isShouldLog() {
        return shouldLog;
    }

    public void setShouldLog(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }

    public String getIgnorePatternsStr() {
        return ignorePatternsStr;
    }

    public void setIgnorePatternsStr(String ignorePatternsStr) {
        if (StringUtils.hasLength(ignorePatternsStr)) {
            String[] array = ignorePatternsStr.split(",");
            if (!ObjectUtils.isEmpty(array)) {
                ignorePatterns = new ArrayList<>(array.length);
                Collections.addAll(ignorePatterns, array);
                log.info("ignorePatterns:{}", ignorePatterns.toString());
            }
        }
        this.ignorePatternsStr = ignorePatternsStr;
    }

    public List<String> getIgnorePatterns() {
        return ignorePatterns;
    }

    public void setIgnorePatterns(List<String> ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
    }

    public static char[] getChars(byte[] bytes, Charset cs) {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

}
