package com.dataservice.doctorsPatients.models.notes;

import org.springframework.stereotype.Component;

import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.patients.Patient;

@Component
public class MapperNote {
    public Note map(NoteDtoInput dto){
        var note = new Note();
        note.setDate(dto.getDate());
        note.setDoctor(Doctor.builder()
            .numPass(dto.getNumPass()).build());
        note.setPatient(Patient.builder()
            .snils(dto.getSnils()).build());
        return note;
    }
    public NoteDtoOutput map(Note m){       
        return NoteDtoOutput.builder()
            .snils(m.getPatient().getSnils())
            .numPass(m.getDoctor().getNumPass())
            .date(m.getDate()).build();
    }
}
