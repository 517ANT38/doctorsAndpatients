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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.apiService.apiHospital.dtos.DateCountNoteDto;
import com.apiService.apiHospital.dtos.DoctorDto;
import com.apiService.apiHospital.dtos.FIODto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class ApiServiceDoctorsController {

    private final RestTemplate template;
    @Value("${data-service.base-url}")
    private String baseUrl;

    @PostMapping("/addDoctor")
    public ResponseEntity<String> save(@RequestBody DoctorDto dto){
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEnitity = new HttpEntity<>(dto,headers);
        try {
            System.out.println(dto);
            template.exchange(baseUrl+"/doctors/addDoctor",HttpMethod.POST,
                httpEnitity,DoctorDto.class);
            return ResponseEntity.ok("Add doctor");
            
        }
        catch(HttpStatusCodeException e){     
                   
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

    @GetMapping("/{numPass}/getDateDayCountNotes")
    public ResponseEntity<List<DateCountNoteDto>> getDateDayCountNotes(@PathVariable("numPass")  long numPass){
        var path = "/doctors/{numPass}/getDateDayCountNotes";
        ParameterizedTypeReference<List<DateCountNoteDto>> v =
             new ParameterizedTypeReference<List<DateCountNoteDto>>() {};
        try {
            var res = template.exchange(baseUrl+path, HttpMethod.GET,
                null, v,Map.of("numPass",numPass));
            return res;
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAll(){
        ParameterizedTypeReference<List<DoctorDto>> v = 
            new ParameterizedTypeReference<List<DoctorDto>>() {};
        return template.exchange(baseUrl+"/doctors", HttpMethod.GET,null,v);

    }

    @GetMapping("/{numPass}")
    public ResponseEntity<DoctorDto> getById(@PathVariable("numPass")long numPass){
        var path ="/doctors/{numPass}";
        try{
            return template.exchange(baseUrl+path, HttpMethod.GET, 
                null, DoctorDto.class, Map.of("numPass",numPass));
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
        
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<DoctorDto>> getDoctorByFio(FIODto fDto){
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEnitity = new HttpEntity<>(fDto,headers);
        ParameterizedTypeReference<List<DoctorDto>> v = 
            new ParameterizedTypeReference<List<DoctorDto>>() {};
        try {
            return template.exchange(baseUrl+"/doctors/getFio", HttpMethod.GET, httpEnitity, v);
        }
        catch(HttpStatusCodeException e){            
            return ResponseEntity
                .status(e.getStatusCode().value())
                .build();
        }
    }

    @GetMapping("/getTop10WithMaxPatients")
    public ResponseEntity<List<DoctorDto>> getTop10WithMaxPatients(){
        ParameterizedTypeReference<List<DoctorDto>> v = 
            new ParameterizedTypeReference<List<DoctorDto>>() {};
        var path = "/doctors/getTop10WithMaxPatients";
        return template.exchange(baseUrl+path, HttpMethod.GET,null,v);
    }
}