package com.apiService.apiHospital.dtos;

import java.time.LocalDate;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class DateAndDoctorDto {
    private LocalDate date;
    private List<DoctorDto> patients;
}
