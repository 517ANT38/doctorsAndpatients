package com.dataservice.doctorsPatients.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dataservice.doctorsPatients.models.notes.MapperNote;
import com.dataservice.doctorsPatients.models.notes.NoteDtoInput;
import com.dataservice.doctorsPatients.services.DoctorAndNoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopicListener {

    private final DoctorAndNoteService service;
    private final MapperNote mapperNote;

    @KafkaListener(topics = "hospital-topic", groupId = "group_id_1")
    void consume(NoteDtoInput dto){
        String res = service.saveNote(mapperNote.map(dto));
        log.info(res);
    }
}

