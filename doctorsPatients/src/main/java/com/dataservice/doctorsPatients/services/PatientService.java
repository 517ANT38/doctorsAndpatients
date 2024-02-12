package com.dataservice.doctorsPatients.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataservice.doctorsPatients.exceptions.PatientDublicateException;
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

    public Patient getById(Integer idPatient){
        return patientRepo.findById(idPatient)
        .orElseThrow(() -> new PatientNotFoundException("Patient not found with id="+ idPatient));
    }

    @Transactional
    public Patient savePatient(Patient p){
        if (patientRepo.existsBySnils(p.getSnils())) {
            throw new PatientDublicateException("Patient exits with snils=" + p.getSnils());
        }
        return patientRepo.save(p);
    }

    public List<Patient> getTop10MaxCountNotes(){
        return patientRepo.findAll().stream()
            .filter(x -> x.getNotes().size() > 6)
            .limit(10)
            .toList();
    }
}
