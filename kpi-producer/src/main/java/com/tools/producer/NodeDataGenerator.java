package com.tools.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Slf4j
@Service
public class NodeDataGenerator {

    private final Random random = new Random();
    private final Set<Integer> usedNodeIds = new HashSet<>();
    private final Set<Integer> usedNetworkIds = new HashSet<>();

    @Autowired
    private NodeDataProducer kafkaProducer;

    @Scheduled(fixedRate = 2000) // Run every 2 seconds
    public void generateMetrics() {
        int nodeId = generateUniqueId(usedNodeIds, 100);
        int networkId = generateUniqueId(usedNetworkIds, 10);

        NodeData data = new NodeData(
                nodeId, // unique nodeId
                networkId, // unique networkId
                round(random.nextDouble() * 100), // latency rounded to 2 decimal places
                round(random.nextDouble() * 10), // throughput rounded to 2 decimal places
                round(random.nextDouble()), // errorRate rounded to 2 decimal places
                LocalDateTime.now() // timestamp
        );

        kafkaProducer.sendMessage(String.valueOf(nodeId), data); // Publish data to Kafka topic
        log.info("published: " + data);
    }

    private int generateUniqueId(Set<Integer> usedIds, int max) {
        int id;
        do {
            id = random.nextInt(max);
        } while (usedIds.contains(id));
        usedIds.add(id);
        return id;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
