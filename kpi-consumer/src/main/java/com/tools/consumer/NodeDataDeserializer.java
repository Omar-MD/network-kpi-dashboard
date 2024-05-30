package com.tools.consumer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NodeDataDeserializer implements Deserializer<NodeData> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NodeData deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                log.info("Null received at deserializing");
                return null;
            }
            log.info("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), NodeData.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to NodeData");
        }
    }

}