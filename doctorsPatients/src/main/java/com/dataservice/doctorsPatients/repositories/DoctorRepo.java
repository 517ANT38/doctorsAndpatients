package com.dataservice.doctorsPatients.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.util.FIODto;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
    Optional<Doctor> findByFio(FIODto fio);
}
