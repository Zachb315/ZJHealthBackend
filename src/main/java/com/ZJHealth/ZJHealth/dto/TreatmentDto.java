package com.ZJHealth.ZJHealth.dto;

import lombok.Data;

@Data
public class TreatmentDto {
    private Long id;
    private Long apptId;
    private String treatmentName;
    private String treatmentReason;
}
