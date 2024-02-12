package com.dataservice.doctorsPatients.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dataservice.doctorsPatients.exceptions.DoctorNotFoundException;
import com.dataservice.doctorsPatients.models.doctors.Doctor;
import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.util.DateCountNoteDto;
import com.dataservice.doctorsPatients.repositories.DoctorRepo;
import com.dataservice.doctorsPatients.repositories.NoteRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorAndNoteService {
    
    private final DoctorRepo doctorRepo;
    private final NoteRepo noteRepo;

    public Doctor saveDoctor(Doctor doctor){
        return doctorRepo.save(doctor);
    }

    public Note saveNote(Note note){
        
        return noteRepo.save(note);
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
