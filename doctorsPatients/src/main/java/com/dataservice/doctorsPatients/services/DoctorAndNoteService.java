package com.dataservice.doctorsPatients.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        if (d == null || !doctorRepo.existsByNumPass(d.getNumPass())) {
            return "Doctor is null or not found";
        }
        if (p == null || !patientRepo.existsBySnils(p.getSnils())) {
            return "Patient is null or not found";
        }
        var dFromDb = doctorRepo.findByNumPass(d.getNumPass()).get();
        var pFromDb = patientRepo.findBySnils(p.getSnils()).get();
        note.setDoctor(dFromDb);
        note.setPatient(pFromDb);
        var notesD = Optional.ofNullable(dFromDb.getNotes())
            .orElse(new HashSet<>());
        notesD.add(note);
        dFromDb.setNotes(notesD);
        var notesP = Optional.ofNullable(pFromDb.getNotes())
            .orElse(new HashSet<>());
        notesP.add(note);
        pFromDb.setNotes(notesP);

        doctorRepo.save(dFromDb);
        patientRepo.save(pFromDb);
        noteRepo.save(note);
        return "Save note";
    }

    public List<Doctor> getByFio(FIODto fio){
        return doctorRepo.findByFio(fio);
    }

    public List<Doctor> getAll(){
        return doctorRepo.findAll();
    }

    public Doctor getByNumPass(long numPass){
        return doctorRepo.findByNumPass(numPass)
        .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with numPass="+ numPass));
    }

    public List<Doctor> getTop10WithMaxPatients(){
        return doctorRepo.findAll().stream()
            .filter(x -> x.getNotes().size() > 10)
            .limit(10)
            .toList();
    }
    
    public List<DateCountNoteDto> getDateDayCountNotes(long numPass) {
        var d = doctorRepo.findByNumPass(numPass)
            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with numPass="+ numPass));
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
