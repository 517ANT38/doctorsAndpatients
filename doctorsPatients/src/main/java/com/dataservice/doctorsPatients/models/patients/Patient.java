package com.dataservice.doctorsPatients.models.patients;

import java.time.LocalDate;
import java.util.Set;

import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.util.FIODto;

import jakarta.persistence.Column;
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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private FIODto fio;
    @Column(unique = true)
    private long snils;
    private LocalDate dateBirth;
    private LocalDate regHospital;
    @OneToMany(mappedBy = "patient")
    private Set<Note> notes;
}
