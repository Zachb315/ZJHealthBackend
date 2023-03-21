package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.LabCreateDto;
import com.ZJHealth.ZJHealth.dto.LabResultDto;
import com.ZJHealth.ZJHealth.dto.LabReturnDto;
import com.ZJHealth.ZJHealth.model.Lab;
import com.ZJHealth.ZJHealth.service.LabService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/lab")
@Data
public class LabController {
    private final LabService labService;
    @Autowired
    private HttpServletResponse response;
    @PostMapping("/create")
    public void createLab(@RequestBody LabCreateDto labDto) {

        labService.createLab(labDto);
    }

    @GetMapping("/list")
    public List<Lab> listall() {
        List<Lab> labs = labService.listall();
        return labs;
    }

    @GetMapping("/view/labs")
    public List<LabReturnDto> listByDoctor() {
        return labService.listByDoctor();
    }

    @PutMapping("/update/result")
    public void updateResult(@RequestBody LabResultDto labdto) {
        labService.updateResult(labdto);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Void> viewPdf(@PathVariable Long id) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","filename=Prescription.pdf");
        labService.viewPdf(response, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
