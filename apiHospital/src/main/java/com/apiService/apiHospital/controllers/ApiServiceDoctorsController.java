package com.apiService.apiHospital.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apiService.apiHospital.dtos.DateAndPatientDto;
import com.apiService.apiHospital.dtos.DoctorAndPatients;
import com.apiService.apiHospital.dtos.DoctorDto;
import com.apiService.apiHospital.dtos.FIODto;
import com.apiService.apiHospital.util.ApiHelper;
import com.apiService.apiHospital.util.MessageRes;
import com.apiService.apiHospital.util.SenderHelper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class ApiServiceDoctorsController {

    private final SenderHelper senderHelper;
    private final ApiHelper helper;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.doctor.topic}")
    private String topic;


    @PostMapping("/addDoctor")
    public ResponseEntity<MessageRes> addDoctor(@RequestBody DoctorDto dto){
        return senderHelper.send(topic, dto);       
        
    }

    @GetMapping("/{numPass}/getDateDayNotes")
    public ResponseEntity<List<DateAndPatientDto>> getDateDayNotes(@PathVariable("numPass")  long numPass){
        var path = "/doctors/{numPass}/getDateDayNotes";
        return helper.requestLst(baseUrl+path, 
            Map.of("numPass",numPass), 
            DateAndPatientDto.class);
        
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAll(){
        
        return helper.requestLst(baseUrl+"/doctors", null,DoctorDto.class);

    }

    @GetMapping("/{numPass}")
    public ResponseEntity<DoctorDto> getById(@PathVariable("numPass")long numPass){
        var path ="/doctors/{numPass}";
        return helper.request(baseUrl+path, 
            Map.of("numPass",numPass), 
            DoctorDto.class );
        
        
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<DoctorDto>> getDoctorByFio(FIODto fDto){
        
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("/doctors/getFio?")
            .append("name=").append(fDto.getName())
            .append("&family=").append(fDto.getFamily())
            .append("&patronymic=").append(fDto.getPatronymic());       
        
        return helper.requestLst(builder.toString(), null, DoctorDto.class);
        
    }

    @GetMapping("/getTop10WithMaxPatients")
    public ResponseEntity<List<DoctorAndPatients>> getTop10WithMaxPatients(){
        
        var path = "/doctors/getTop10WithMaxPatients";
        return helper.requestLst(baseUrl+path,null,DoctorAndPatients.class);
    }
}
