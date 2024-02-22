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
public class PatientAndDoctors {
    private PatientDto patient;
    private List<DoctorDto> doctors;
    private long countDoctors;
}
