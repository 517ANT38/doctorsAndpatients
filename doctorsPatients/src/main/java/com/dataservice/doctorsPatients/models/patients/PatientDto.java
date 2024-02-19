package com.dataservice.doctorsPatients.models.patients;

import java.time.LocalDate;

import com.dataservice.doctorsPatients.models.util.FIODto;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    private String snils;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBirth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regHospital;
}
