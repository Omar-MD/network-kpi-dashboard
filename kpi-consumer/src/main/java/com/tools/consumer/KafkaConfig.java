package com.tools.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
//import com.tools.dashboard.dto.NodeData;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, NodeData> myConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, NodeDataDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NodeData> myKafkaListenerContainerFactory(
            ConsumerFactory<String, NodeData> f) {
        ConcurrentKafkaListenerContainerFactory<String, NodeData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(f);
        return factory;
    }

    //New

//            Map<String, Object> props = new HashMap<>();
//            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:8085");
//            props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
//            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//            JsonDeserializer<NodeData> deserializer = new JsonDeserializer<>(NodeData.class);
//            deserializer.addTrustedPackages("*");
//
//            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
//
//            return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
//        }
//
//        @Bean
//        public ConcurrentKafkaListenerContainerFactory<String, NodeData> myKafkaListenerContainerFactory() {
//            ConcurrentKafkaListenerContainerFactory<String, NodeData> factory = new ConcurrentKafkaListenerContainerFactory<>();
//            factory.setConsumerFactory(myConsumerFactory());
//            return factory;
//        }


}
