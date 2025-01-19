package com.junlog;

import com.junlog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class JunlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunlogApplication.class, args);

    }


}
