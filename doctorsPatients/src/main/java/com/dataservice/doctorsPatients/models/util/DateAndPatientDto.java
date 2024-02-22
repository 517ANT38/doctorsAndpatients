package com.dataservice.doctorsPatients.models.util;

import java.time.LocalDate;

import java.util.List;
import com.dataservice.doctorsPatients.models.patients.PatientDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class DateAndPatientDto {
    private LocalDate date;
    private List<PatientDto> patients;
}
