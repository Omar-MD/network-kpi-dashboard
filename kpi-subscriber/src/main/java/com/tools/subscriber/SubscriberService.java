package com.tools.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SubscriberService {
    public static void main(String[] args) {
        SpringApplication.run(SubscriberService.class, args);
        log.info("Subscriber Service Running...");
    }
}
