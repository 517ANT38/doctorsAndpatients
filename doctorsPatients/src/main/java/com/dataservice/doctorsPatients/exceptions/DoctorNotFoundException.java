package com.dataservice.doctorsPatients.exceptions;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String e){
        super(e);
    }
}
