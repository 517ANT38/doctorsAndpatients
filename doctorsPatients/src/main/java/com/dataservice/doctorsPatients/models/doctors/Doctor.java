package com.dataservice.doctorsPatients.models.doctors;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.util.FIODto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Data
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private FIODto fio;
    private String jobTitle;
    private LocalDate dateEmp;
    @OneToMany(mappedBy = "doctor")
    private Set<Note> notes;
}
