package com.dataservice.doctorsPatients.exceptions;

public class PatientDublicateException extends RuntimeException {
    public PatientDublicateException(String e){
        super(e);
    }
}
