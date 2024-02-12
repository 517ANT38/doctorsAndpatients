package com.dataservice.doctorsPatients.models.doctors;

import com.dataservice.doctorsPatients.models.util.FIODto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class DoctorDto {
    private String name;
    private FIODto fio;
    private String jobTitle;
    private String dateEmp;
}
