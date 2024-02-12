package com.dataservice.doctorsPatients.models.patients;

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
    private String name;
    private String family;
    private String patronymic;
    private LocalDate dateBirth;
    private LocalDate regHospital;
}
