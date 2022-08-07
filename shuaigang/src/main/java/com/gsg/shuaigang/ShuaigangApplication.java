package com.gsg.shuaigang;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.gsg.shuaigang.mapper")
@ComponentScan("com.gsg")
@EnableEurekaClient
@EnableFeignClients
@Slf4j
public class ShuaigangApplication {

    public static void main(String[] args) {
        ConfigurableEnvironment environment = SpringApplication.run(ShuaigangApplication.class, args).getEnvironment();
        log.info("启动成功！！");
        log.info("服务名:\t{} \t地址: \t[http://localhost]:[{}]", environment.getProperty("spring.application.name"),environment.getProperty("server.port"));
    }

    @Bean
    // 使用负载均衡
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
