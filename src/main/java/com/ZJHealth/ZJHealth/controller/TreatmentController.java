package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.TreatmentDto;
import com.ZJHealth.ZJHealth.model.Treatment;
import com.ZJHealth.ZJHealth.service.TreatmentService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatment")
@Data
public class TreatmentController {
    private final TreatmentService treatmentService;

    @PostMapping("/create")
    public void createTreatment(@RequestBody TreatmentDto treatmentDto) {
        treatmentService.createTreatment(treatmentDto);
    }

    @GetMapping("/list")
    public List<Treatment> listAll() {
        return treatmentService.listAll();
    }
}
