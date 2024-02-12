package com.dataservice.doctorsPatients.models.notes;

import java.time.LocalDateTime;


import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.patients.Patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "idDoctor",referencedColumnName = "id")
    private Doctor doctor;
    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "idPatient",referencedColumnName = "id")
    private Patient patient;
    private LocalDateTime date;
}
