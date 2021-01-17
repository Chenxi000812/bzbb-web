package com.chenxi.bzbb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.chenxi.bzbb.domain.dao")
@EnableTransactionManagement
public class BzbbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BzbbApplication.class, args);
    }

}
