package com.tools.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NodeDataProducer {

    private static final String TOPIC_NAME = "node-kpi-data";

    @Autowired
    private KafkaTemplate<String, NodeData> kafkaTemplate;

    public void sendMessage(String key, NodeData value) {
        kafkaTemplate.send(TOPIC_NAME, key, value);
    }
}