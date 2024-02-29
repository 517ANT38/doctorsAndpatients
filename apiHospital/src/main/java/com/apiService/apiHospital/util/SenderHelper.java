package com.apiService.apiHospital.util;

import java.time.LocalDateTime;
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

    public ResponseEntity<MessageRes> send(String topic,Object obj){
        try {
            var s = mapper.writeValueAsString(obj);
            kafkaTemplate.send(topic, topic, s).get();
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageRes.builder()
                    .msg("Added data successfully")
                    .timeStamp(LocalDateTime.now().toString())
                    .build());
        } catch (JsonProcessingException e) {
            log.error("Json error", e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        } catch (InterruptedException e) {
            log.error("Interrupted error", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (ExecutionException e) {
            log.error("Execution error", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
