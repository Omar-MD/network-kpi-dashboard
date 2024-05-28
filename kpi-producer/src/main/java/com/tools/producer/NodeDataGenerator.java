package com.tools.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class NodeDataGenerator {

    private final Random random = new Random();

    @Autowired
    private NodeDataProducer kafkaProducer;

    @PostConstruct
    public void startGeneratingMetrics() {
        new Thread(() -> {
            while (true) {
                generateMetrics();
                try {
                    Thread.sleep(2000); // Sleep for 2 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    log.error("Thread was interrupted", e);
                }
            }
        }).start();
    }

    public void generateMetrics() {
        int nodeId = random.nextInt(100) + 1; // Node ID range 1-150
        int networkId = random.nextInt(5) + 1; // Network ID range 1-15

        NodeData data = new NodeData(
                nodeId, // nodeId
                networkId, // networkId
                round(random.nextDouble() * 100), // latency rounded to 2 decimal places
                round(random.nextDouble() * 10), // throughput rounded to 2 decimal places
                round(random.nextDouble()), // errorRate rounded to 2 decimal places
                LocalDateTime.now() // timestamp
        );

        kafkaProducer.sendMessage(String.valueOf(nodeId), data); // Publish data to Kafka topic
        log.info("published: " + data);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
