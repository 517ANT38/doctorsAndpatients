package com.apiService.apiHospital.configs;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestTemplate;

import com.apiService.apiHospital.dtos.NoteDtoInput;

@Configuration
public class Config {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    RestTemplate template(){
        return new RestTemplate();
    }
    @Bean
    ProducerFactory<String,NoteDtoInput> producerFactory(){
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        var typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.addTrustedPackages("com.apiHospital.apiPatients.dtos");
        JsonDeserializer<NoteDtoInput> deserializer = new JsonDeserializer<>(NoteDtoInput.class);
        deserializer.typeMapper(typeMapper); 
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
        
    }

    @Bean
    KafkaTemplate<String,NoteDtoInput> kafkaTemplate(ProducerFactory<String,NoteDtoInput> factory){
        return new KafkaTemplate<>(factory);
    }

}
