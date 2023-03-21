package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.*;
import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import com.ZJHealth.ZJHealth.service.AppointmentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@Data
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ZJUserRepository zjUserRepository;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentCreateDto createDto) {
        try {
            Principal currentUser = request.getUserPrincipal();
            ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
            appointmentService.createAppointment(createDto, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (IllegalStateException ex) {
            if (ex.getMessage()=="Appointment Conflict") {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/list/all")
    public List<Appointment> listall() {
        List<Appointment> appointments = appointmentService.listall();
        return appointments;
    }

    @GetMapping("/list/unassigned")
    public List<Appointment> listUnassigned() {
        List<Appointment> appointments = appointmentService.listUnassigned();
        return appointments;
    }

    @GetMapping("/user/appointments")
    public List<AppointmentReturnDto> listUsersAppointments() {
        List<AppointmentReturnDto> appointments = appointmentService.listUsersAppointsments();
        return appointments;
    }

    @PutMapping("/addDoctor/{id}")
    public void updateAppointmentDoctor(@PathVariable Long id) {
        appointmentService.updateAppointmentDoctor(id);
    }

    @GetMapping("/find/{id}")
    public Appointment findAppt(@PathVariable Long id) {
        return appointmentService.findAppt(id);
    }

    @PutMapping("/update/{id}/diagnosis")
    public void updateDiagnosis(@RequestBody DiagnosisDto diagnosisDto, @PathVariable Long id) {
        appointmentService.updateDiagnosis(diagnosisDto, id);
    }

    @PutMapping("/update/{id}/treatment")
    public void updateTreatment(@RequestBody TreatmentDto treatmentDto, @PathVariable Long id) {
        appointmentService.updateTreatment(treatmentDto, id);
    }

    @PutMapping("/review/{id}")
    public void reviewAppointment(@RequestBody AppointmentReturnDto reviewDto, @PathVariable Long id) {
        appointmentService.reviewAppointment(reviewDto, id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @GetMapping("/admin/reviews")
    public List<AppointmentReturnDto> viewApptReviews() {
        return appointmentService.viewApptReviews();

    }
}
