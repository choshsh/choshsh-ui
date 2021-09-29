package com.choshsh.itsmv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItsmvApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItsmvApplication.class, args);
    }

}