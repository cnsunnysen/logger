package com.mysteelsoft.logger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysteelsoft.logger.other.ResponseWrapper;
import com.mysteelsoft.logger.service.LoggingService;
import com.mysteelsoft.logger.vo.LoggingQueryVo;
import com.mysteelsoft.logger.vo.RequestLoggingVo;
import com.mysteelsoft.logger.vo.RequestLoggingWhole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志api
 *
 * @author shanyesen
 */
@Api("日志对外接口")
@RestController
@RequestMapping("/logger")
public class LoggerController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LoggingService loggingService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("请求日志查询列表")
    @RequestMapping(value = "/requestList", method = RequestMethod.POST)
    public ResponseWrapper<Page<RequestLoggingVo>> requestList(@RequestBody LoggingQueryVo queryVo) throws Exception {
        logger.info("in controller /logger/requestList");
        Page<RequestLoggingVo> data = loggingService.requestListData(queryVo);
        return new ResponseWrapper<Page<RequestLoggingVo>>().success(data);
    }

    @ApiOperation("请求日志详情")
    @RequestMapping(value = "/requestDetail", method = RequestMethod.POST)
    public ResponseWrapper<RequestLoggingWhole> requestDetail(@RequestBody LoggingQueryVo queryVo) throws Exception {
        logger.info("in controller /logger/requestDetail");
        RequestLoggingWhole data = loggingService.requestDetail(queryVo);
        ResponseWrapper result = new ResponseWrapper<RequestLoggingWhole>().success(data);
        return result;
    }


}
