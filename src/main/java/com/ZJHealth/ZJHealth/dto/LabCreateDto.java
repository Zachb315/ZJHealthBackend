package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.Doctor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LabCreateDto {
    private Long id;
    private String testName;
    private LocalDateTime date;
    private Doctor doctor;
    private String patientEmail;
    private String result;
}
