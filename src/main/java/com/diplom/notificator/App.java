package com.diplom.notificator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.diplom.notificator")
@EnableConfigurationProperties
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
