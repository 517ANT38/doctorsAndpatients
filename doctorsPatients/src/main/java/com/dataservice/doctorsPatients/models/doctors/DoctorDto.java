package com.dataservice.doctorsPatients.models.doctors;

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
    private String family;
    private String patronymic;
    private String jobTitle;
    private String dateEmp;
}
