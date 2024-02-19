package com.apiService.apiHospital.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class DoctorDto {
    private FIODto fio;
    private long  numPass;
    private String jobTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEmp;
}
