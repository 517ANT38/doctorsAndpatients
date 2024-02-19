package com.dataservice.doctorsPatients.models.patients;


import org.springframework.stereotype.Component;

@Component
public class MapperPatient {
    public Patient map(PatientDto dto){
        return Patient.builder()
            .dateBirth(dto.getDateBirth())
            .fio(dto.getFio())
            .regHospital(dto.getRegHospital())
            .snils(dto.getSnils())
            .build();
    }
    public PatientDto map(Patient dto){
        return PatientDto.builder()
            .dateBirth(dto.getDateBirth())
            .fio(dto.getFio())
            .snils(dto.getSnils())
            .regHospital(dto.getRegHospital())
            .build();
    }
}
