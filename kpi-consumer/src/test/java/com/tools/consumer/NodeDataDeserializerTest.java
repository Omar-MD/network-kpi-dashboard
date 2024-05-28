package com.tools.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataDeserializerTest {

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private NodeDataDeserializer nodeDataDeserializer;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDeserializeWithNullData() {
        NodeData nodeData = nodeDataDeserializer.deserialize("test-topic", null);

        assertNull(nodeData);
    }

    @Test
    void testDeserializeWithMalformedJson() {
        byte[] data = "{invalidJson}".getBytes(StandardCharsets.UTF_8);

        assertThrows(SerializationException.class, () -> {
            nodeDataDeserializer.deserialize("test-topic", data);
        });
    }

    @Test
    void testDeserializeWithSerializationException() {
        byte[] data = "invalidData".getBytes();

        assertThrows(SerializationException.class, () -> {
            nodeDataDeserializer.deserialize("test-topic", data);
        });
    }
}
