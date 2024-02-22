package com.apiService.apiHospital.dtos;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class DoctorAndPatients {
    private DoctorDto doctor;
    private List<PatientDto> patients;
    private long countPatients;
}
