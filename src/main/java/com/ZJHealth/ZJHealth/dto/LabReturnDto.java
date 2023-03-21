package com.ZJHealth.ZJHealth.dto;


import com.ZJHealth.ZJHealth.model.Doctor;
import com.ZJHealth.ZJHealth.model.MedicalRecord;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class LabReturnDto {
    private Long id;
    private String testName;
    private LocalDateTime date;
    private String result;
    private Doctor doctor;
    private String patientFName;
    private String patientLName;
    private MedicalRecord record;
}
