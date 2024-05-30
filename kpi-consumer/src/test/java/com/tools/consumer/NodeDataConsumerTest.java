package com.tools.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class NodeDataConsumerTest {

    @Mock
    private NodeDataRepo nodeDataRepo;

    @InjectMocks
    private NodeDataConsumer nodeDataConsumer;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testConsumer() {

        String key = "key";
        String topic = "node-kpi-data";
        long timestamp = System.currentTimeMillis();
        NodeData nodeData = new NodeData(/* provide appropriate constructor arguments */);
        ConsumerRecord<String, NodeData> consumerRecord = new ConsumerRecord<>(topic, 0, 0, key, nodeData);

        nodeDataConsumer.consumer(key, topic, timestamp, consumerRecord);

        verify(nodeDataRepo, times(1)).save(nodeData);
    }
}
