package com.dataservice.doctorsPatients.exceptions;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String e){
        super(e);
    }
}
