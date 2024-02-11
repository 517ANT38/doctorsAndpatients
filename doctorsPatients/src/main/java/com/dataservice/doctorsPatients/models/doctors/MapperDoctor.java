package com.dataservice.doctorsPatients.models.doctors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapperDoctor {
    @InheritInverseConfiguration
    Doctor map(DoctorDto dto);
} 