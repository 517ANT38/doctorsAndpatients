package com.dataservice.doctorsPatients.models.doctors;

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
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String family;
    private String patronymic;
    private String jobTitle;
    private Date dateEmp;
    @OneToMany(mappedBy = "doctor")
    private Set<Note> notes;
}
