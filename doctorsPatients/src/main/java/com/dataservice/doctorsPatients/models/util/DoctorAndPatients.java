package com.dataservice.doctorsPatients.models.util;

import java.util.List;

import com.dataservice.doctorsPatients.models.doctors.DoctorDto;
import com.dataservice.doctorsPatients.models.patients.PatientDto;

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
