package com.dataservice.doctorsPatients.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dataservice.doctorsPatients.models.doctors.DoctorDto;
import com.dataservice.doctorsPatients.models.doctors.MapperDoctor;
import com.dataservice.doctorsPatients.models.notes.MapperNote;
import com.dataservice.doctorsPatients.models.notes.NoteDtoInput;
import com.dataservice.doctorsPatients.models.patients.MapperPatient;
import com.dataservice.doctorsPatients.models.patients.PatientDto;
import com.dataservice.doctorsPatients.services.DoctorAndNoteService;
import com.dataservice.doctorsPatients.services.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopicListener {

    private final DoctorAndNoteService doctorAndNoteService;
    private final PatientService patientService;
    private final MapperNote mapperNote;
    private final MapperPatient mapperPatient;
    private final MapperDoctor mapperDoctor;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${kafka.note.topic}",concurrency = "2", groupId = "${kafka.consumer.id}")
    void consumeNote(String dto){
        try {
            var d = mapper.readValue(dto,NoteDtoInput.class);
            String res = doctorAndNoteService.saveNote(mapperNote.map(d));
            log.info(res);
        }catch(JsonProcessingException e){
            log.error("Mapping error:", e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.doctor.topic}",concurrency = "2", groupId = "${kafka.consumer.id}")
    void consumeDoctor(String dto) throws JsonMappingException{
        try {
            var d = mapper.readValue(dto,DoctorDto.class);
            String res = doctorAndNoteService.saveDoctor(mapperDoctor.map(d));
            log.info(res);
        }catch(JsonProcessingException e){
            log.error("Mapping error:", e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.patient.topic}",concurrency = "2", groupId = "${kafka.consumer.id}")
    void consumePatient(String dto){
        try {
            var d = mapper.readValue(dto,PatientDto.class);
            String res = patientService.savePatient(mapperPatient.map(d));
            log.info(res);
        }catch(JsonProcessingException e){
            log.error("Mapping error:", e.getMessage());
        }
    }
}

