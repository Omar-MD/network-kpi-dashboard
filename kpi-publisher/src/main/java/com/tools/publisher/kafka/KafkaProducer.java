package com.tools.publisher.kafka;

import com.tools.publisher.dto.PerformanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final String TOPIC_NAME = "performance-data-topic";

    @Autowired
    private KafkaTemplate<String, PerformanceData> kafkaTemplate;

    public void sendMessage(PerformanceData data) {
        kafkaTemplate.send(TOPIC_NAME, data);
    }
}