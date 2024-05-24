package com.tools.subscriber.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class MyObjectConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MyObjectConsumer.class);

    @KafkaListener(topics = "topic-example", groupId = "myGroup")
    public void consumer(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
            Object value) {
        logger.info("********** MyObjectConsumer.consumer() consumed message: key {}, topic {}, timestamp {}, value {}", key, topic, timestamp, value);
    }
}
