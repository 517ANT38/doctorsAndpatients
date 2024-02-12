package com.dataservice.doctorsPatients.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dataservice.doctorsPatients.exceptions.DoctorDublicateException;
import com.dataservice.doctorsPatients.exceptions.DoctorNotFoundException;
import com.dataservice.doctorsPatients.exceptions.PatientDublicateException;
import com.dataservice.doctorsPatients.exceptions.PatientNotFoundException;
import com.dataservice.doctorsPatients.exceptions.util.ResponseError;

@ControllerAdvice
public class AppControllerAdvice {
    
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ResponseError> handleException(DoctorNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseError(e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ResponseError> handleException(PatientNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseError(e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler(DoctorDublicateException.class)
    public ResponseEntity<ResponseError> handleException(DoctorDublicateException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseError(e.getMessage(), LocalDateTime.now().toString()));
    }
    @ExceptionHandler(PatientDublicateException.class)
    public ResponseEntity<ResponseError> handleException(PatientDublicateException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseError(e.getMessage(), LocalDateTime.now().toString()));
    }

}
