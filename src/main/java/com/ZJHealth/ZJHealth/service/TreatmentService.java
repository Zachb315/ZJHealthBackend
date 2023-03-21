package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.TreatmentDto;
import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.MedicalRecord;
import com.ZJHealth.ZJHealth.model.Treatment;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.AppointmentRepository;
import com.ZJHealth.ZJHealth.repository.MedicalRecordRepository;
import com.ZJHealth.ZJHealth.repository.TreatmentRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final ZJUserRepository zjUserRepository;

    public void createTreatment(TreatmentDto newTreatment) {
        Appointment appt = appointmentRepository.findById(newTreatment.getApptId()).get();
        MedicalRecord record = medicalRecordRepository.findByPatientId(appt.getPatient().getId());
        ZJUser patient = appt.getPatient();
        Treatment treatment = new Treatment();
        treatment.setTreatmentName(newTreatment.getTreatmentName());
        treatment.setTreatmentReason(newTreatment.getTreatmentReason());
        treatment.setAppointment(appt);
        treatment.setRecord(record);
        treatmentRepository.save(treatment);
        record.addTreatment(treatment);
        medicalRecordRepository.save(record);
        patient.setRecord(record);
        zjUserRepository.save(patient);
    }

    public List<Treatment> listAll() {
        return treatmentRepository.findAll();
    }
}
