package com.mysteelsoft.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shanyesen
 */
@EnableTransactionManagement
@SpringBootApplication
public class LoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggerApplication.class, args);
    }

}

