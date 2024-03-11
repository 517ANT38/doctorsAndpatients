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

import com.dataservice.doctorsPatients.exceptions.DoctorNotFoundException;
import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.doctors.MapperDoctor;
import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.patients.MapperPatient;
import com.dataservice.doctorsPatients.models.patients.PatientDto;
import com.dataservice.doctorsPatients.models.util.DateAndPatientDto;
import com.dataservice.doctorsPatients.models.util.DoctorAndPatients;
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
    private final MapperPatient mapperPatient;
    private final MapperDoctor mapperDoctor;
    private final NoteRepo noteRepo;

    @Transactional
    public String saveDoctor(Doctor doctor){
        doctorRepo.save(doctor);
        return "Doctor added";
    }

    @Transactional
    public String saveNote(Note note){
        var d = note.getDoctor();
        var p = note.getPatient();
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

        noteRepo.save(note);
        return "Save note";
    }

    public List<Doctor> getByFio(FIODto fio){
        return doctorRepo.findByFio(fio);
    }

    public List<Doctor> getAll(){
        return doctorRepo.findAll();
    }

    public Doctor getByNumPass(String numPass){
        return doctorRepo.findByNumPass(numPass)
        .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with numPass="+ numPass));
    }

    public List<DoctorAndPatients> getTop10WithMaxPatients(){
        return doctorRepo.findAll().stream()
            .filter(x -> x.getNotes().size() > 3)
            .map(x -> new DoctorAndPatients(mapperDoctor.map(x), x.getNotes().stream()
                .map(Note::getPatient)
                .map(mapperPatient::map)
                .toList(), x.getNotes().size()))
            .sorted((x,y) -> Long.compare(x.getCountPatients(), y.getCountPatients()))
            .limit(10)
            .toList();
    }
    
    public List<DateAndPatientDto> getDateDayCountNotes(String numPass) {
        var d = doctorRepo.findByNumPass(numPass)
            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with numPass="+ numPass));
        var setNotes = d.getNotes();
        Map<LocalDate,List<PatientDto>> map = new HashMap<>();
        for (Note note : setNotes) {
            var date = note.getDate().toLocalDate();
            var arr = map.getOrDefault(date, new ArrayList<>());
            arr.add(mapperPatient.map(note.getPatient()));
            map.put(date, arr);
        }
        List<DateAndPatientDto> res = new ArrayList<>();
        for (var item : map.entrySet()) {
            res.add(new DateAndPatientDto(item.getKey(), item.getValue()));
        }
        res.sort((x,y) -> x.getDate().compareTo(y.getDate()));
        return res;
    }
    
}
