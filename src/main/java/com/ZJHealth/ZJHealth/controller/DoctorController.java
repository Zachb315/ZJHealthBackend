package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.AppointmentReturnDto;
import com.ZJHealth.ZJHealth.dto.UpdateDoctorDto;
import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.Doctor;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.service.DoctorService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Data
public class DoctorController {
    private final DoctorService doctorService;

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/update")
    public ResponseEntity<Void> updateRole(UpdateDoctorDto doctor) throws IOException {
        doctorService.updateRole(doctor);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/update/{docId}/department/{deptId}")
    public void updateDepartment(@PathVariable Long docId, @PathVariable Long deptId) {
        doctorService.updateDepartment(docId, deptId);
    }


    @GetMapping("/list")
    public ResponseEntity<List<ZJUser>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.listAll());
    }


    @GetMapping("/find")
    public ResponseEntity<List<ZJUser>> findDoctor(@RequestParam(name="name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findDoctor(name));
    }
    @PreAuthorize("hasAuthority('doctor')")
    @GetMapping("/appointments/list")
    public ResponseEntity<List<AppointmentReturnDto>> listAppointments() {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.listAppointments());
    }

    @PreAuthorize("hasAuthority('doctor')")
    @GetMapping("/doctor/statistics")
    public ResponseEntity<List<AppointmentReturnDto>> doctorStatistics() {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.doctorStatistics());
    }

}
