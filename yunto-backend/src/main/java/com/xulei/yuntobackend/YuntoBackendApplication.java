package com.xulei.yuntobackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.xulei.yuntobackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class YuntoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuntoBackendApplication.class, args);
    }

}
