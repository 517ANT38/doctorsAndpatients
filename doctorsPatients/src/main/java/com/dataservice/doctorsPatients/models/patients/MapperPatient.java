package com.dataservice.doctorsPatients.models.patients;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapperPatient {
    @InheritInverseConfiguration
    Patient map(PatientDto dto);
    PatientDto map(Patient dto);
}
