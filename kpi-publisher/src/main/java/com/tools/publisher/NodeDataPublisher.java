package com.tools.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NodeDataPublisher {

    private static final String TOPIC_NAME = "node-kpi-data";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String key, Object value) {
        kafkaTemplate.send(TOPIC_NAME, key, value);
    }
}