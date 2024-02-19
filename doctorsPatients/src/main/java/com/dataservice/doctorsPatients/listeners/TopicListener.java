package com.dataservice.doctorsPatients.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dataservice.doctorsPatients.models.notes.MapperNote;
import com.dataservice.doctorsPatients.models.notes.NoteDtoInput;
import com.dataservice.doctorsPatients.services.DoctorAndNoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopicListener {

    private final DoctorAndNoteService service;
    private final MapperNote mapperNote;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "hospital-topic", groupId = "group_id_1")
    void consume(String dto){
        try {
            var d = mapper.readValue(dto,NoteDtoInput.class);
            String res = service.saveNote(mapperNote.map(d));
            log.info(res);
        }catch(Throwable e){
            log.error("Mapping error:", e);
        }
    }
}

