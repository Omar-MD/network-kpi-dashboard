package com.tools.publisher;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class PublisherService {
    public static void main(String[] args) {
        SpringApplication.run(PublisherService.class, args);
        log.info("KPI Publisher Running...");
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("node-kpi-data")
                .partitions(10)
                .replicas(1)
                .build();
    }
}
