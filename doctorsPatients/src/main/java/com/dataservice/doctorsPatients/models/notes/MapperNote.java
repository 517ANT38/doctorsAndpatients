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
            .id(dto.getDoctorId()).build());
        note.setPatient(Patient.builder()
            .id(dto.getPatientId()).build());
        return note;
    }
    public NoteDtoOutput map(Note m){       
        return NoteDtoOutput.builder().doctor(m.getDoctor().getFio().toString())
            .patient(m.getPatient().getFio().toString())
            .date(m.getDate()).build();
    }
}
