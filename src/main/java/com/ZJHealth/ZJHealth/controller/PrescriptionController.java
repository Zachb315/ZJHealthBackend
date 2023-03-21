package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.PrescriptionDto;
import com.ZJHealth.ZJHealth.model.Prescription;
import com.ZJHealth.ZJHealth.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    @Autowired
    private HttpServletResponse response;

    @PostMapping("/create")
    public ResponseEntity<Void> createPrescription(@RequestBody PrescriptionDto prescriptionReq) {
        prescriptionService.createPrescription(prescriptionReq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Prescription>> listAll() {
        List<Prescription> prescriptions = prescriptionService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(prescriptions);
    }

    @GetMapping("/list/patient")
    public ResponseEntity<List<Prescription>> listByPatient() {
        List<Prescription> prescriptions = prescriptionService.listByPatient();
        return ResponseEntity.status(HttpStatus.OK).body(prescriptions);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Void> createPdf(@PathVariable Long id) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","filename=Prescription.pdf");
        prescriptionService.createPdf(response, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
