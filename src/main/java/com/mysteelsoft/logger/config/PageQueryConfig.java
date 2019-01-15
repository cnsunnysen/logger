package com.mysteelsoft.logger.config;

import com.esteel.common.dao.PageQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shanyesen
 */
@Configuration
public class PageQueryConfig {

    @Bean
    public PageQuery pageQuery() {
        return new PageQuery();
    }
}