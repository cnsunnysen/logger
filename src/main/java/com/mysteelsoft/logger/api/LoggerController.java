package com.mysteelsoft.logger.api;

import com.mysteelsoft.logger.vo.LoggerQueryVo;
import com.mysteelsoft.logger.vo.LoggerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志api
 * @author shanyesen
 */
@Api("日志对外接口")
@RestController
@RequestMapping("/logger")
public class LoggerController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("日志查询列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<LoggerVo> list(LoggerQueryVo queryVo) {
        List<LoggerVo> result = new ArrayList<>();
        result.add(new LoggerVo());
        return result;
    }


}
