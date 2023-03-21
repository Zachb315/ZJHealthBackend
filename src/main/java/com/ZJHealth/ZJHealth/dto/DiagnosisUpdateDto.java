package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.Diagnosis;
import lombok.Data;

@Data
public class DiagnosisUpdateDto {
    private Appointment appointment;
    private DiagnosisDto diagnosis;
}
