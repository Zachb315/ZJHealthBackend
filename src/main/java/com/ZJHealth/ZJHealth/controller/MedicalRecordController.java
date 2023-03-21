package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.MedicalRecordDto;
import com.ZJHealth.ZJHealth.model.Diagnosis;
import com.ZJHealth.ZJHealth.model.Lab;
import com.ZJHealth.ZJHealth.model.MedicalRecord;
import com.ZJHealth.ZJHealth.model.Treatment;
import com.ZJHealth.ZJHealth.service.MedicalRecordService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/record")
@Data
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @GetMapping("/view/{id}")
    public MedicalRecord viewRecord(@PathVariable Long id) {

        return medicalRecordService.viewRecord(id);

    }

    @PutMapping("/update")
    public void updateRecord(@RequestBody MedicalRecordDto recordDto) {
        medicalRecordService.addToRecord(recordDto);
    }

    @GetMapping("/view/current")
    public MedicalRecord viewCurrentUsersRecord() {
        return medicalRecordService.viewCurrentUsersRecord();
    }
}
