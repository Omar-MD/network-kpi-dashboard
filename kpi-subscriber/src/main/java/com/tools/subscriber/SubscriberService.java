package com.tools.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class SubscriberService {
    @Autowired
    static NodeRepo nRepo;
    public static void main(String[] args) {
        SpringApplication.run(SubscriberService.class, args);
        System.out.println("Subscriber Service Running...");
       // log.info("Subscriber Service Running...");
        nRepo.save(new NodeData(1, 2, 4.4, 3.4, 5.0, LocalDateTime.now()));
    }
}
