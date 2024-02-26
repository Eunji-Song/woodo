package com.example.woodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WoodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WoodoApplication.class, args);
    }

}
