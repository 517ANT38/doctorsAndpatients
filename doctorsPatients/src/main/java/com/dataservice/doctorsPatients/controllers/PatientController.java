package com.dataservice.doctorsPatients.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataservice.doctorsPatients.models.patients.MapperPatient;
import com.dataservice.doctorsPatients.models.patients.PatientDto;
import com.dataservice.doctorsPatients.models.util.FIODto;
import com.dataservice.doctorsPatients.models.util.PatientAndDoctors;
import com.dataservice.doctorsPatients.services.PatientService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final MapperPatient mapper;
   

    @GetMapping("/{snils}")
    public ResponseEntity<PatientDto> getBySnils(@PathVariable String snils){
        return ResponseEntity.ok(mapper.map(patientService.getBySnils(snils)));
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll(){
        return ResponseEntity.ok(patientService.getAll().stream()
            .map(mapper::map)
            .toList());
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<PatientDto>> getByFio(FIODto fDto){
        return ResponseEntity.ok(patientService.getByFio(fDto).stream()
            .map(mapper::map)
            .toList());
    }

    @GetMapping("/getTop10MaxCountNotes")
    public ResponseEntity<List<PatientAndDoctors>> getTop10MaxCountNotes(){
        return ResponseEntity.ok(patientService.getTop10MaxCountNotes());
    }
}
