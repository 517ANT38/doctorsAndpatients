package com.dataservice.doctorsPatients.models.notes;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class NoteDtoInput {
    private Integer doctorId;
    private Integer patientId;
    private Date date;
}
