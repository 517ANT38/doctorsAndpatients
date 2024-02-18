package com.dataservice.doctorsPatients.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataservice.doctorsPatients.models.doctors.DoctorDto;
import com.dataservice.doctorsPatients.models.doctors.MapperDoctor;
import com.dataservice.doctorsPatients.models.util.DateCountNoteDto;
import com.dataservice.doctorsPatients.models.util.FIODto;
import com.dataservice.doctorsPatients.services.DoctorAndNoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/doctors")
@RequiredArgsConstructor
public class DoctorController {
    
    private final DoctorAndNoteService danS;
    private final MapperDoctor mapper;

    @PostMapping("/addDoctor")
    public ResponseEntity<DoctorDto> save(@RequestBody DoctorDto dto){
        return ResponseEntity.ok(mapper.map(danS.saveDoctor(mapper.map(dto))));
    }

    @GetMapping("/{idDoctor}/getDateDayCountNotes")
    public ResponseEntity<List<DateCountNoteDto>> getDateDayCountNotes(@PathVariable("idDoctor") Integer idDoctor){
        return ResponseEntity.ok(danS.getDateDayCountNotes(idDoctor));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAll(){
        return ResponseEntity.ok(danS.getAll().stream()
            .map(mapper::map)
            .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getById(@PathVariable("id")Integer id){
        return ResponseEntity.ok(mapper.map(danS.getById(id)));
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<DoctorDto>> getDoctorByFio(@RequestBody FIODto fDto){
        return ResponseEntity.ok(danS.getByFio(fDto).stream()
            .map(mapper::map)
            .toList());
    }

    @GetMapping("/getTop10WithMaxPatients")
    public ResponseEntity<List<DoctorDto>> getTop10WithMaxPatients(){
        return ResponseEntity.ok(danS.getTop10WithMaxPatients().stream()
            .map(mapper::map)
            .toList());
    }
    

}
