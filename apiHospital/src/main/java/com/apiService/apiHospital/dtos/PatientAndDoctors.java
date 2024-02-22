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
public class PatientAndDoctors {
    private PatientDto patient;
    private List<DoctorDto> doctors;
    private long countDoctors;
}
