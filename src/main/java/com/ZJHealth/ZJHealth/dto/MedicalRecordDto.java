package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.Diagnosis;
import com.ZJHealth.ZJHealth.model.Lab;
import com.ZJHealth.ZJHealth.model.Treatment;
import lombok.Data;

@Data
public class MedicalRecordDto {
    private Diagnosis diagnosis;
    private Treatment treatment;
    private Lab lab;


}
