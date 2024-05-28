package com.tools.producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tools.producer.NodeData;
import com.tools.producer.NodeDataGenerator;
import com.tools.producer.NodeDataProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class NodeDataGeneratorTest {

    @Mock
    private NodeDataProducer kafkaProducer;

    @InjectMocks
    private NodeDataGenerator nodeDataGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateMetrics() {
        doNothing().when(kafkaProducer).sendMessage(any(String.class), any(NodeData.class));
        nodeDataGenerator.generateMetrics();
        verify(kafkaProducer, times(1)).sendMessage(any(String.class), any(NodeData.class));
    }
}
