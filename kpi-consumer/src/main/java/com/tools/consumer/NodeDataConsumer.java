package com.tools.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NodeDataConsumer {

    @Autowired
    private NodeDataRepo nodeDataRepo;

    @KafkaListener(topics = "node-kpi-data", groupId = "group1", containerFactory = "myKafkaListenerContainerFactory")
    public void consumer(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
            ConsumerRecord<String, NodeData> record) {

        // Save the NodeData to the database
        nodeDataRepo.save(record.value());

        // Log msg
        log.info("Consumed msg: topic={}, key={}, value={}", topic, key, record.value());
    }
}
