package com.mysteelsoft.logger.config;

import com.mysteelsoft.logger.filter.HttpLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author shanyesen
 */
@Configuration
@EnableAsync
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setFilter(httpLoggingFilter());
        filterRegistrationBean.setName("httpLoggingFilter111");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("ignorePatternsStr", "/**/*.js,/**/*.gif,/**/*.jpg" +
                ",/**/*.png,/**/*.css,/**/*.ico,/**/swagger-resources/**,/**/webjars/**" +
                ",/,/**/v2/**,/csrf");
        return filterRegistrationBean;
    }

    @Bean
    public HttpLoggingFilter httpLoggingFilter() {
        return new HttpLoggingFilter();
    }

    /**
     * 线程池 用于异步调用
     *
     * @return
     */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
