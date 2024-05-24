package com.tools.publisher;

import java.util.Random;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
        return TopicBuilder.name("topic1")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        log.info("Published Msg {topic: topic1, data: test}");
        return args -> {
            template.send("topic1", "test");
        };
    }
}

@Slf4j
@Component
class MessagePublisher {
    private final KafkaTemplate<String, String> template;
    private final Random random = new Random();

    public MessagePublisher(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @Scheduled(fixedRate = 5000) // Adjust the rate as needed
    public void publishMessage() {
        String message = generateRandomMessage();
        template.send("topic1", message);
        log.info("Published Msg {topic: topic1, data: " + message + "}");
    }

    private String generateRandomMessage() {
        int number = random.nextInt(1000);
        return "Random Message " + number;
    }
}
