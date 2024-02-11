package com.dataservice.doctorsPatients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataservice.doctorsPatients.models.patients.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> {
    
}
