package com.jarvlis.jarojbackendquestionservice;

import com.jarvlis.jarojbackendquestionservice.messagemq.MyMessageProducer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.jarvlis.jarojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.jarvlis")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.jarvlis.jarojbackendserviceclient.service"})
public class JarojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JarojBackendQuestionServiceApplication.class, args);
    }
}
