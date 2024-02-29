package com.dataservice.doctorsPatients.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataservice.doctorsPatients.exceptions.DoctorNotFoundException;
import com.dataservice.doctorsPatients.exceptions.PatientNotFoundException;
import com.dataservice.doctorsPatients.models.doctors.DoctorDto;
import com.dataservice.doctorsPatients.models.doctors.MapperDoctor;
import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.patients.MapperPatient;
import com.dataservice.doctorsPatients.models.patients.Patient;
import com.dataservice.doctorsPatients.models.util.DateAndDoctorDto;
import com.dataservice.doctorsPatients.models.util.FIODto;
import com.dataservice.doctorsPatients.models.util.PatientAndDoctors;
import com.dataservice.doctorsPatients.repositories.PatientRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    
    private final PatientRepo patientRepo;
    private final MapperDoctor mapperDoctor;
    private final MapperPatient mapperPatient;

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
        patientRepo.save(p);
        return "Patient aded";
    }

    public List<PatientAndDoctors> getTop10MaxCountNotes(){
        return patientRepo.findAll().stream()
            .filter(x -> x.getNotes().size() > 3)
            .map(x -> PatientAndDoctors.builder()
                .countDoctors(x.getNotes().size())
                .doctors(x.getNotes().stream()
                .map(Note::getDoctor)
                .map(mapperDoctor::map).toList())
                .patient(mapperPatient.map(x)).build())
            .limit(10)
            .toList();
    }

    public List<DateAndDoctorDto>  getDateDayNotes(String snils) {
        var d = patientRepo.findBySnils(snils)
            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with snils="+ snils));
        var setNotes = d.getNotes();
        Map<LocalDate,List<DoctorDto>> map = new HashMap<>();
        for (Note note : setNotes) {
            var date = note.getDate().toLocalDate();
            var arr = map.getOrDefault(date, new ArrayList<>());
            arr.add(mapperDoctor.map(note.getDoctor()));
            map.put(date, arr);
        }
        List<DateAndDoctorDto> res = new ArrayList<>();
        for (var item : map.entrySet()) {
            res.add(new DateAndDoctorDto(item.getKey(), item.getValue()));
        }
        res.sort((x,y) -> x.getDoctors().size() - y.getDoctors().size());
        return res;
    }
}
