package com.tools.publisher.datasimulator;

import com.tools.publisher.dto.PerformanceData;
import com.tools.publisher.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class DataSimulatorGenerator {

    private final Random random = new Random();
    private final Set<Integer> usedNodeIds = new HashSet<>();
    private final Set<Integer> usedNetworkIds = new HashSet<>();

    @Autowired
    private KafkaProducer kafkaProducer;

    @Scheduled(fixedRate = 2000) // Run every 2 seconds
    public void generateMetrics() {
        int nodeId = generateUniqueId(usedNodeIds, 100);
        int networkId = generateUniqueId(usedNetworkIds, 10);

        PerformanceData data = new PerformanceData(
                nodeId,                           // unique nodeId
                networkId,                        // unique networkId
                round(random.nextDouble() * 100), // latency rounded to 2 decimal places
                round(random.nextDouble() * 10),  // throughput rounded to 2 decimal places
                round(random.nextDouble()),       // errorRate rounded to 2 decimal places
                LocalDateTime.now()               // timestamp
        );

        kafkaProducer.sendMessage(data); // Publish data to Kafka topic
        System.out.println(data);
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
