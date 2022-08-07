package com.gsg.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableEurekaServer
public class ServerApplication {

    public static void main(String[] args) {
        ConfigurableEnvironment environment = SpringApplication.run(ServerApplication.class, args).getEnvironment();
        System.out.println("启动成功！！");
        System.out.println("服务名: "+ environment.getProperty("spring.application.name") +
                " \n 地址: \thttp://localhost:" + environment.getProperty("server.port"));

    }

}
