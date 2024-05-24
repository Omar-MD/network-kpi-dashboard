package com.tools.publisher;

import com.tools.publisher.datasimulator.DataSimulatorGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PublisherService {
    public static void main(String[] args) {

        System.out.println("Hello");
        SpringApplication.run(PublisherService.class, args);
    }
}
