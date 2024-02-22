package com.dataservice.doctorsPatients.models.util;

import java.time.LocalDate;
import java.util.List;

import com.dataservice.doctorsPatients.models.doctors.DoctorDto;

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
