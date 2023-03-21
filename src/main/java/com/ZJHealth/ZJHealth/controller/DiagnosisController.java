package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.DiagnosisDto;
import com.ZJHealth.ZJHealth.model.Diagnosis;
import com.ZJHealth.ZJHealth.model.Treatment;
import com.ZJHealth.ZJHealth.service.DiagnosisService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diagnosis")
@Data
public class DiagnosisController {
    private final DiagnosisService diagnosisService;


    @PostMapping("/create")
    public void createDiagnosis(@RequestBody DiagnosisDto diagnosisDto) {
        diagnosisService.createDiagnosis(diagnosisDto);
    }

}
