package com.dataservice.doctorsPatients.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataservice.doctorsPatients.models.patients.Patient;
import com.dataservice.doctorsPatients.models.util.FIODto;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> {
    List<Patient> findByFio(FIODto fio);
    Optional<Patient> findBySnils(long snils);
    boolean existsBySnils(long snils);
}
