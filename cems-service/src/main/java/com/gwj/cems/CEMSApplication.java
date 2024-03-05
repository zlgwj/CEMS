package com.gwj.cems;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.gwj")
@MapperScan("com.gwj.cems.mapper")
@EnableScheduling
public class CEMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(CEMSApplication.class, args);
    }
}
