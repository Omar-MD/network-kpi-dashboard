package com.tools.subscriber;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NodeDataConsumer {

    @KafkaListener(topics = "node-kpi-data", groupId = "group1")
    public void consumer(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
            ConsumerRecord<String, Object> record) {

        log.info("Consumed message: timestamp={}, topic={}, key={}, value={}", timestamp, topic, key, record.value());
        // NodeData nd = (NodeData) record.value();
        // log.warn(String.valueOf(nd.getNodeId()));
    }
}
