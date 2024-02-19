package com.dataservice.doctorsPatients.models.doctors;

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
public class DoctorDto {
    private FIODto fio;
    private String jobTitle;
    private long  numPass;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEmp;
}
