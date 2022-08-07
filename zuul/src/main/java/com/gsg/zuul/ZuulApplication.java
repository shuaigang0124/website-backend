package com.gsg.zuul;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy   //开启zuul网关代理 可以使用的过滤器更多
@EnableRedisHttpSession
@Slf4j
@ComponentScan("com.gsg")
@MapperScan("com.gsg.zuul.mapper")
public class ZuulApplication {

    public static void main(String[] args) {
        ConfigurableEnvironment environment = SpringApplication.run(ZuulApplication.class, args).getEnvironment();
        log.info("启动成功！！");
        log.info("服务名:\t{} \t地址: \t[http://localhost]:[{}]", environment.getProperty("spring.application.name"),environment.getProperty("server.port"));
    }


}
