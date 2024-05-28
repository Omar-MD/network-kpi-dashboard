package com.tools.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tools.consumer.NodeData;

public class NodeDataTest {

    private NodeData nodeData;

    @BeforeEach
    void setUp() {
        nodeData = new NodeData();
    }

    @Test
    void testNodeId() {
        nodeData.setNodeId(15);
        assertEquals(15, nodeData.getNodeId());
    }

    @Test
    void testNetworkId() {
        nodeData.setNetworkId(15);
        assertEquals(15, nodeData.getNetworkId());
    }

    @Test
    void testLatency() {
        nodeData.setLatency(20.3);
        assertEquals(20.3, nodeData.getLatency());
    }

    @Test
    void testThroughput() {
        nodeData.setThroughput(180);
        assertEquals(180, nodeData.getThroughput());
    }

    @Test
    void testErrorRate() {
        nodeData.setErrorRate(0.02);
        assertEquals(0.02, nodeData.getErrorRate());
    }

    @Test
    void testTimestamp() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
        nodeData.setTimestamp(dateTime);
        assertEquals(dateTime, nodeData.getTimestamp());
    }

    @Test
    void testConstructor() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
        nodeData = new NodeData(1, 1, 20.3, 180, 0.02, dateTime);
        assertEquals(1, nodeData.getNodeId());
    }
}

