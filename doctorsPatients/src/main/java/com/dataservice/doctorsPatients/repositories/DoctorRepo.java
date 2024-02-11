package com.dataservice.doctorsPatients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataservice.doctorsPatients.models.doctors.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
    
}
