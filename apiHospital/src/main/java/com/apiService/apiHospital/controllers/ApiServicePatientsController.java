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
import com.apiService.apiHospital.dtos.NoteDto;
import com.apiService.apiHospital.dtos.PatientAndDoctors;
import com.apiService.apiHospital.dtos.PatientDto;
import com.apiService.apiHospital.util.GetterRequest;
import com.apiService.apiHospital.util.MessageRes;
import com.apiService.apiHospital.util.Publisher;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*",
    methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiServicePatientsController {
    
    private final GetterRequest helper;
    private final Publisher senderHelper;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.note.topic}")
    private String noteTopic;
    @Value("${kafka.patient.topic}")
    private String patientTopic;

    @PostMapping("/addNoteInDoctor")
    @ApiResponses({
        @ApiResponse(responseCode="201"),
        @ApiResponse(responseCode="500"),
        @ApiResponse(responseCode="422")
    })
    public ResponseEntity<MessageRes> addNote(@RequestBody NoteDto dto){
        
        return senderHelper.send(noteTopic,dto.getSnils(),dto);
    }

    @PostMapping("/addPatient")
    @ApiResponses({
        @ApiResponse(responseCode="201"),
        @ApiResponse(responseCode="500"),
        @ApiResponse(responseCode="422")
    })
    public ResponseEntity<MessageRes> addPatient(@RequestBody PatientDto dto){
        
        return senderHelper.send(patientTopic,dto.getSnils(),dto);
    }

    @GetMapping("/{snils}")
    @ApiResponses({
        @ApiResponse(responseCode="404",content = @Content(schema = @Schema())),
        @ApiResponse(responseCode="200")
    })
    public ResponseEntity<PatientDto> getById(@PathVariable String snils){
        var path ="/patients/{snils}";       
        return helper.request(baseUrl+path, Map.of("snils",snils),PatientDto.class);
        
    }


    @GetMapping("/{snils}/getDateDayNotes")
    @ApiResponses({
        @ApiResponse(responseCode="404",content = @Content(schema = @Schema())),
        @ApiResponse(responseCode="200")
    })
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
        builder.append("/patients/getFio?")
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
