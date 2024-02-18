package com.apiHospital.apiPatients.dtos;

import java.time.LocalDate;


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
