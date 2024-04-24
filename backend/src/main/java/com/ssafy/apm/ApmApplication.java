package com.ssafy.apm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApmApplication.class, args);
    }

}
