package com.tools.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProducerService {

    public static void main(String[] args) {
        SpringApplication.run(ProducerService.class, args);
        log.info("KPI Publisher Running...");
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("node-kpi-data")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
