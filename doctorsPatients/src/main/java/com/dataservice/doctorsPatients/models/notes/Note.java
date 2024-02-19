package com.dataservice.doctorsPatients.models.notes;

import java.time.LocalDateTime;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.patients.Patient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "idDoctor",referencedColumnName = "id",nullable = false)
    private Doctor doctor;
    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "idPatient",referencedColumnName = "id",nullable = false)
    private Patient patient;
    private LocalDateTime date;
}
