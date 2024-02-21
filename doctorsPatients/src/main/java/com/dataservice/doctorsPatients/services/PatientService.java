package com.dataservice.doctorsPatients.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataservice.doctorsPatients.exceptions.PatientNotFoundException;
import com.dataservice.doctorsPatients.models.patients.Patient;
import com.dataservice.doctorsPatients.models.util.FIODto;
import com.dataservice.doctorsPatients.repositories.PatientRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    
    private final PatientRepo patientRepo;

    public List<Patient> getByFio(FIODto fio){
        return patientRepo.findByFio(fio);
    }

    public List<Patient> getAll(){
        return patientRepo.findAll();
    }

    public Patient getBySnils(String snils){
        return patientRepo.findBySnils(snils)
            .orElseThrow(() -> new PatientNotFoundException("Patient not found with snils="+ snils));
    }

    @Transactional
    public String savePatient(Patient p){
        if (patientRepo.existsBySnils(p.getSnils())) {
            return "Patient exits with snils=" + p.getSnils();
        }
        patientRepo.save(p);
        return "Patient aded";
    }

    public List<Patient> getTop10MaxCountNotes(){
        return patientRepo.findAll().stream()
            .filter(x -> x.getNotes().size() > 3)
            .limit(10)
            .toList();
    }
}
