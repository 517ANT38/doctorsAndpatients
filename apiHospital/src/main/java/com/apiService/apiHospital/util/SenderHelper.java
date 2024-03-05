package com.apiService.apiHospital.util;

import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SenderHelper {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper mapper;

    public ResponseEntity<MessageRes> send(String topic,String key,Object obj){
        try {
            var s = mapper.writeValueAsString(obj);
            kafkaTemplate.send(topic, key, s).get();
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageRes("Added data successfully"));
        } catch (JsonProcessingException e) {

            log.error("Json error", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new MessageRes("Unprocessable Content"));

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            log.error("Interrupted error", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageRes("Internal server error"));

        } catch (ExecutionException e) {
            log.error("Execution error", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageRes("Internal server error"));
        }
    }
}
