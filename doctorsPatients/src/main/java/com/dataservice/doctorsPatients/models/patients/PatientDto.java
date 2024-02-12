package com.dataservice.doctorsPatients.models.patients;

import java.time.LocalDate;

import com.dataservice.doctorsPatients.models.util.FIODto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PatientDto {
    private FIODto fio;
    private long snils;
    private LocalDate dateBirth;
    private LocalDate regHospital;
}
