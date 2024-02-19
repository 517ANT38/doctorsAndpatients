package com.dataservice.doctorsPatients.models.doctors;


import org.springframework.stereotype.Component;

@Component
public class MapperDoctor {
    public Doctor map(DoctorDto dto){
        return Doctor.builder()
            .dateEmp(dto.getDateEmp())
            .numPass(dto.getNumPass())
            .fio(dto.getFio())
            .jobTitle(dto.getJobTitle())
            .build();
    }
    public DoctorDto map(Doctor dto){
        return DoctorDto.builder()
            .dateEmp(dto.getDateEmp())
            .numPass(dto.getNumPass())
            .fio(dto.getFio())
            .jobTitle(dto.getJobTitle())
            .build();
    }
} 