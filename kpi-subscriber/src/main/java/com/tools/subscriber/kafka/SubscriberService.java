package com.tools.subscriber.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubscriberService {
    public static void main(String[] args) {
        SpringApplication.run(SubscriberService.class, args);
        System.out.println("Subscriber Service Started Successfully");
    }
}
