package com.apiService.apiHospital.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.apiService.apiHospital.dtos.DateAndDoctorDto;
import com.apiService.apiHospital.dtos.FIODto;
import com.apiService.apiHospital.dtos.NoteDtoInput;
import com.apiService.apiHospital.dtos.PatientAndDoctors;
import com.apiService.apiHospital.dtos.PatientDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*",
    methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiServicePatientsController {
    
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.note.topic}")
    private String noteTopic;
    @Value("${kafka.patient.topic}")
    private String patientTopic;

    @PostMapping("/addNoteInDoctor")
    @SneakyThrows
    public ResponseEntity<String> addNote(@RequestBody NoteDtoInput dto){
        kafkaTemplate.send(noteTopic,mapper.writeValueAsString(dto));
        return ResponseEntity.ok("Note added successfully");
    }

    @PostMapping("/addPatient")
    @SneakyThrows
    public ResponseEntity<String> addPatient(@RequestBody PatientDto dto){
        kafkaTemplate.send(patientTopic,mapper.writeValueAsString(dto));
        return ResponseEntity.ok("Patient added successfully");
    }

    @GetMapping("/{snils}")
    public ResponseEntity<PatientDto> getById(@PathVariable String snils){
        var path ="/patients/{snils}";
        try{
            return restTemplate.exchange(baseUrl+path, HttpMethod.GET, 
                null, PatientDto.class, Map.of("snils",snils));
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }


    @GetMapping("/{snils}/getDateDayNotes")
    public ResponseEntity<List<DateAndDoctorDto>> getDateDayNotes(@PathVariable("snils") String snils){
        var path = "/patients/{snils}/getDateDayNotes";
        try {
            ParameterizedTypeReference<List<DateAndDoctorDto>> v = 
                new ParameterizedTypeReference<List<DateAndDoctorDto>>() {};
            return restTemplate.exchange(baseUrl + path,HttpMethod.GET,
                null,v,Map.of("snils",snils));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll(){
        ParameterizedTypeReference<List<PatientDto>> v = 
            new ParameterizedTypeReference<List<PatientDto>>() {};
        return restTemplate.exchange(baseUrl + "/patients", HttpMethod.GET,null,v);
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<PatientDto>> getByFio(FIODto fDto){
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEnitity = new HttpEntity<>(fDto,headers);
        ParameterizedTypeReference<List<PatientDto>> v = 
            new ParameterizedTypeReference<List<PatientDto>>() {};
        try {
            return restTemplate.exchange(baseUrl+"/patients/getFio", HttpMethod.GET, httpEnitity, v);
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

    @GetMapping("/getTop10MaxCountNotes")
    public ResponseEntity<List<PatientAndDoctors>> getTop10MaxCountNotes(){
        ParameterizedTypeReference<List<PatientAndDoctors>> v = 
            new ParameterizedTypeReference<List<PatientAndDoctors>>() {};
        return restTemplate.exchange(baseUrl + "/patients/getTop10MaxCountNotes", HttpMethod.GET,null,v);
    }

}
