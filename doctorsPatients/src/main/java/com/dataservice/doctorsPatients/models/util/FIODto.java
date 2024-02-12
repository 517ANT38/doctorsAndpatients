package com.dataservice.doctorsPatients.models.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FIODto {
    private String name;
    private String family;
    private String patronymic;
}
