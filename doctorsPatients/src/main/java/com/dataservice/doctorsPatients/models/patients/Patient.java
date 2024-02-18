package com.dataservice.doctorsPatients.models.patients;

import java.time.LocalDate;
import java.util.Set;

import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.util.FIODto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false)),
            @AttributeOverride(name = "family", column = @Column(name = "family",  nullable = false)),
            @AttributeOverride(name = "patronymic", column = @Column(name ="patronymic"))
    })
    private FIODto fio;
    @Column(unique = true)
    private long snils;
    private LocalDate dateBirth;
    private LocalDate regHospital;
    @OneToMany(mappedBy = "patient")
    private Set<Note> notes;
}
