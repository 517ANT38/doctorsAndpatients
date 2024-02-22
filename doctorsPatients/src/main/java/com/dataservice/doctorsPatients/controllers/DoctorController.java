package com.dataservice.doctorsPatients.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataservice.doctorsPatients.models.doctors.DoctorDto;
import com.dataservice.doctorsPatients.models.doctors.MapperDoctor;
import com.dataservice.doctorsPatients.models.util.DateAndPatientDto;
import com.dataservice.doctorsPatients.models.util.DoctorAndPatients;
import com.dataservice.doctorsPatients.models.util.FIODto;
import com.dataservice.doctorsPatients.services.DoctorAndNoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/doctors")
@RequiredArgsConstructor
public class DoctorController {
    
    private final DoctorAndNoteService danS;
    private final MapperDoctor mapper;

    

    @GetMapping("/{numPass}/getDateDayCountNotes")
    public ResponseEntity<List<DateAndPatientDto>> getDateDayCountNotes(@PathVariable("numPass") long numPass){
        return ResponseEntity.ok(danS.getDateDayCountNotes(numPass));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAll(){
        return ResponseEntity.ok(danS.getAll().stream()
            .map(mapper::map)
            .toList());
    }

    @GetMapping("/{numPass}")
    public ResponseEntity<DoctorDto> getById(@PathVariable("numPass")long numPass){
        return ResponseEntity.ok(mapper.map(danS.getByNumPass(numPass)));
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<DoctorDto>> getDoctorByFio(FIODto fDto){
        return ResponseEntity.ok(danS.getByFio(fDto).stream()
            .map(mapper::map)
            .toList());
    }

    @GetMapping("/getTop10WithMaxPatients")
    public ResponseEntity<List<DoctorAndPatients>> getTop10WithMaxPatients(){
        return ResponseEntity.ok(danS.getTop10WithMaxPatients());
    }
    

}
