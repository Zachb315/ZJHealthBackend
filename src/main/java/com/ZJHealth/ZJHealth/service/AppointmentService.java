package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.*;
import com.ZJHealth.ZJHealth.model.*;
import com.ZJHealth.ZJHealth.repository.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ZJUserRepository zjUserRepository;
    private final AppointmentIntakeRepository appointmentIntakeRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final TreatmentRepository treatmentRepository;
    @Autowired
    private HttpServletRequest request;

    public void createAppointment(AppointmentCreateDto appt, ZJUser patient) {

        Appointment appointment = new Appointment();
        AppointmentIntake intake = appt.getIntake();
        intake.setInsurance(patient.getInsurance());

        appointmentIntakeRepository.save(intake);
        appointment.setAppointmentIntake(appt.getIntake());
        if (appt.getEmails().getDoctorEmail()!="") {

            ZJUser user = zjUserRepository.findByEmailIgnoreCase(appt.getEmails().getDoctorEmail());
            if (user == null) {
                throw new IllegalStateException("Doctor Doesn't Exist");
            }
            else {
                appointment.setDoctor(user.getDoctor());
                List<Appointment> appointmentList = appointmentRepository.findAllByDiagnosisIsNullAndDoctorId(appointment.getDoctor().getId());
                for (Appointment a : appointmentList) {
                    if (Math.abs(Duration.between(a.getDate(), appt.getEmails().getDate()).toMinutes()) < 30) {
                        throw new IllegalStateException("Appointment Conflict");
                    }

                }

            }

        }
        appointment.setPatient(patient);
        appointment.setDate(appt.getEmails().getDate());
        appointment.setType(appt.getEmails().getType());
        appointmentRepository.save(appointment);
        intake.setAppointment(appointment);
        appointmentIntakeRepository.save(intake);
    }

    public List<Appointment> listall() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> listUnassigned() {
        return appointmentRepository.findByDoctorIsNull();
    }

    public void updateAppointmentDoctor(Long id) {
        Appointment appt = appointmentRepository.findById(id).get();
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        appt.setDoctor(user.getDoctor());
        user.getDoctor().addAppointment(appt);
        appointmentRepository.save(appt);

    }

    public List<AppointmentReturnDto> listUsersAppointsments() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        List<Appointment> appointments = appointmentRepository.findAllByPatientId(user.getId());
        List<AppointmentReturnDto> convertAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            AppointmentReturnDto dto = new AppointmentReturnDto();
            if (appt.getDoctor()!=null) {
                dto.setDoctor(appt.getDoctor().getUser());

            }
            dto.setId(appt.getId());
            dto.setPatient(appt.getPatient());
            dto.setDate(appt.getDate());
            dto.setReview(appt.getReview());
            dto.setIntake(appt.getAppointmentIntake());
            dto.setDiagnosis(appt.getDiagnosis());
            dto.setRating(appt.getRating());
            dto.setType(appt.getType());

            convertAppts.add(dto);
        }

        return convertAppts;

    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment findAppt(Long id) {
        return appointmentRepository.findById(id).get();
    }

    public void updateDiagnosis(DiagnosisDto diagnosisDto, Long id) {
        Appointment appt = appointmentRepository.findById(id).get();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisName(diagnosisDto.getDiagnosisName());
        diagnosis.setAppointment(appt);
        diagnosis.setRecord(appt.getPatient().getRecord());
        diagnosisRepository.save(diagnosis);
        appt.setDiagnosis(diagnosis);
        appointmentRepository.save(appt);
        appt.getPatient().getRecord().addDiagnosis(diagnosis);
        medicalRecordRepository.save(appt.getPatient().getRecord());

    }

    public void updateTreatment(TreatmentDto treatmentDto, Long id) {
        Appointment appt = appointmentRepository.findById(id).get();
        Treatment treatment = new Treatment();
        treatment.setTreatmentName(treatmentDto.getTreatmentName());
        treatment.setTreatmentReason(treatmentDto.getTreatmentReason());
        treatment.setAppointment(appt);
        treatment.setRecord(appt.getPatient().getRecord());
        treatmentRepository.save(treatment);
        appt.getPatient().getRecord().addTreatment(treatment);
        medicalRecordRepository.save(appt.getPatient().getRecord());

    }

    public void reviewAppointment(AppointmentReturnDto reviewDto, Long id) {
        Appointment appt = appointmentRepository.findById(id).get();
        appt.setReview(reviewDto.getReview());
        appt.setRating(reviewDto.getRating());
        appointmentRepository.save(appt);
    }


    public List<AppointmentReturnDto> viewApptReviews() {
        List<Appointment> apptList = appointmentRepository.findAllByReviewIsNotNull();
        List<AppointmentReturnDto> dtoList = new ArrayList<>();
        for (Appointment a : apptList) {
            AppointmentReturnDto dto = new AppointmentReturnDto();
            dto.setId(a.getId());
            dto.setReview(a.getReview());
            dto.setType(a.getType());
            dto.setRating(a.getRating());
            dto.setDoctor(a.getDoctor().getUser());
            dto.setPatient(a.getPatient());

            dtoList.add(dto);
        }
        return dtoList;
    }

}
