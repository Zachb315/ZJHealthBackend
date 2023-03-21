package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.AppointmentDto;
import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.Doctor;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ZJUserRepository zjUserRepository;
    @Mock
    private AppointmentIntakeRepository appointmentIntakeRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private DiagnosisRepository diagnosisRepository;
    @Mock
    private TreatmentRepository treatmentRepository;
    @Mock
    private HttpServletRequest request;

    private AppointmentService appointmentService;


    @BeforeEach
    void setUp() {
        appointmentService = new AppointmentService(appointmentRepository, zjUserRepository, appointmentIntakeRepository,
                diagnosisRepository, medicalRecordRepository, treatmentRepository);
    }

    @Test
    void createAppointment() {
        ZJUser patient = new ZJUser();
        patient.setId(1L);
        patient.setEmail("patient@gmail.com");
//        AppointmentDto appt = new AppointmentDto();
//        appt.setPatientEmail(patient.getEmail());
//        appt.setDate(LocalDateTime.of(2022,12, 10, 8, 30, 59));
//        Appointment check = new Appointment();
//        check.setDate(LocalDateTime.of(2022,12, 10, 8, 30, 59));
//        check.setPatient(patient);
//        when(zjUserRepository.findByEmail(appt.getPatientEmail())).thenReturn(patient);
//        appointmentService.createAppointment(appt);
//        verify(appointmentRepository).save(check);
    }

    @Test
    void listall() {
        appointmentService.listall();
        verify(appointmentRepository).findAll();
    }

    @Test
    void listUnassigned() {
        appointmentService.listUnassigned();
        verify(appointmentRepository).findByDoctorIsNull();
    }

    @Test
    void updateAppointmentDoctor() {


    }
}