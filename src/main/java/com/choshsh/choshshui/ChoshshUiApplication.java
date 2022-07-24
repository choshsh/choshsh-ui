package com.choshsh.choshshui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChoshshUiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChoshshUiApplication.class, args);
  }

}
