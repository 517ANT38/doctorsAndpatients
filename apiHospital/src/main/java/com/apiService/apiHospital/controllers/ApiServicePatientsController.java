package com.apiService.apiHospital.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.apiService.apiHospital.dtos.DateAndDoctorDto;
import com.apiService.apiHospital.dtos.FIODto;
import com.apiService.apiHospital.dtos.NoteDtoInput;
import com.apiService.apiHospital.dtos.PatientAndDoctors;
import com.apiService.apiHospital.dtos.PatientDto;
import com.apiService.apiHospital.util.ApiHelper;
import com.apiService.apiHospital.util.MessageRes;
import com.apiService.apiHospital.util.SenderHelper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*",
    methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiServicePatientsController {
    
    private final ApiHelper helper;
    private final SenderHelper senderHelper;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.note.topic}")
    private String noteTopic;
    @Value("${kafka.patient.topic}")
    private String patientTopic;

    @PostMapping("/addNoteInDoctor")
    public ResponseEntity<MessageRes> addNote(@RequestBody NoteDtoInput dto){
        
        return senderHelper.send(noteTopic,dto.getSnils(),dto);
    }

    @PostMapping("/addPatient")
    public ResponseEntity<MessageRes> addPatient(@RequestBody PatientDto dto){
        
        return senderHelper.send(noteTopic,dto.getSnils(),dto);
    }

    @GetMapping("/{snils}")
    public ResponseEntity<PatientDto> getById(@PathVariable String snils){
        var path ="/patients/{snils}";       
        return helper.request(baseUrl+path, Map.of("snils",snils),PatientDto.class);
        
    }


    @GetMapping("/{snils}/getDateDayNotes")
    public ResponseEntity<List<DateAndDoctorDto>> getDateDayNotes(@PathVariable("snils") String snils){
        var path = "/patients/{snils}/getDateDayNotes";        
        return helper.requestLst(baseUrl + path,
            Map.of("snils",snils),
            DateAndDoctorDto.class);
        
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll(){
        
        return helper.requestLst(baseUrl + "/patients", null,PatientDto.class);
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<PatientDto>> getByFio(FIODto fDto){
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("/doctors/getFio?")
            .append("name=").append(fDto.getName())
            .append("&family=").append(fDto.getFamily())
            .append("&patronymic=").append(fDto.getPatronymic());
        return helper.requestLst(builder.toString(), null,PatientDto.class);
       
    }

    @GetMapping("/getTop10MaxCountNotes")
    public ResponseEntity<List<PatientAndDoctors>> getTop10MaxCountNotes(){
        var path = "/patients/getTop10MaxCountNotes";
        return helper.requestLst(baseUrl + path ,null,PatientAndDoctors.class);
    }

}
