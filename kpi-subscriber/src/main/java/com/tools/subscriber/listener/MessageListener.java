package com.tools.subscriber.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @KafkaListener(topics = "example-topic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received Message in group myGroup: " + message);
    }
}
