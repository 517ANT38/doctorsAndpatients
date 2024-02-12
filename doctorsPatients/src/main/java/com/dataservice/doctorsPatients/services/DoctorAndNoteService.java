package com.dataservice.doctorsPatients.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataservice.doctorsPatients.exceptions.DoctorDublicateException;
import com.dataservice.doctorsPatients.exceptions.DoctorNotFoundException;
import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.util.DateCountNoteDto;
import com.dataservice.doctorsPatients.models.util.FIODto;
import com.dataservice.doctorsPatients.repositories.DoctorRepo;
import com.dataservice.doctorsPatients.repositories.NoteRepo;
import com.dataservice.doctorsPatients.repositories.PatientRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorAndNoteService {
    
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final NoteRepo noteRepo;

    @Transactional
    public Doctor saveDoctor(Doctor doctor){
        if(doctorRepo.existsByNumPass(doctor.getNumPass()))
            throw new DoctorDublicateException("Doctor exits with numPass="+doctor.getNumPass());
        return doctorRepo.save(doctor);
    }

    @Transactional
    public String saveNote(Note note){
        var d = note.getDoctor();
        var p = note.getPatient();
        if (d == null || !doctorRepo.existsById(d.getId())) {
            return "Doctor is null or not found";
        }
        if (p == null || !patientRepo.existsById(p.getId())) {
            return "Patient is null or not found";
        }
        noteRepo.save(note);
        return "Save note";
    }

    public List<Doctor> getByFio(FIODto fio){
        return doctorRepo.findByFio(fio);
    }

    public List<Doctor> getAll(){
        return doctorRepo.findAll();
    }

    public Doctor getById(Integer idDoctor){
        return doctorRepo.findById(idDoctor)
        .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id="+ idDoctor));
    }

    public List<Doctor> getTop10WithMaxPatients(){
        return doctorRepo.findAll().stream()
            .filter(x -> x.getNotes().size() > 10)
            .limit(10)
            .toList();
    }
    
    public List<DateCountNoteDto> getDateDayCountNotes(Integer idDoctor) {
        var d = doctorRepo.findById(idDoctor)
            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id="+ idDoctor));
        var setNotes = d.getNotes();
        Map<LocalDate,Integer> map = new HashMap<>();
        for (Note note : setNotes) {
            var date = note.getDate().toLocalDate();
            var count = map.getOrDefault(date, 0) + 1;
            map.put(date, count);
        }
        List<DateCountNoteDto> res = new ArrayList<>();
        for (var item : map.entrySet()) {
            res.add(new DateCountNoteDto(item.getKey(), item.getValue()));
        }
        res.sort((x,y) -> x.getCount() - y.getCount());
        return res;
    }
    
}
