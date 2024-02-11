package com.dataservice.doctorsPatients.models.patients;

import java.util.Date;
import java.util.Set;

import com.dataservice.doctorsPatients.models.notes.Note;

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
    private String name;
    private String family;
    private String patronymic;
    private Date dateBirth;
    private Date regHospital;
    @OneToMany(mappedBy = "patient")
    private Set<Note> notes;
}
