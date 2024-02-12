package com.dataservice.doctorsPatients.models.util;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class DateCountNoteDto {
    private LocalDate date;
    private int count;
}
