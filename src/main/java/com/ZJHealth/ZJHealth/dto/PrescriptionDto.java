package com.ZJHealth.ZJHealth.dto;

import lombok.Data;

@Data
public class PrescriptionDto {
    private Long id;
    private String name;
    private int quantity;
    private String pharmacy;
    private String condition;
    private String patientEmail;
}
