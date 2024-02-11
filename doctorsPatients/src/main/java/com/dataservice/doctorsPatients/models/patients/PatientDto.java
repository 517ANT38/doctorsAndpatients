package com.dataservice.doctorsPatients.models.patients;

import java.util.Date;

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
    private Date dateBirth;
    private Date regHospital;
}
