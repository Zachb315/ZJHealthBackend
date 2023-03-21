package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.DiagnosisDto;
import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.Diagnosis;
import com.ZJHealth.ZJHealth.model.MedicalRecord;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.AppointmentRepository;
import com.ZJHealth.ZJHealth.repository.DiagnosisRepository;
import com.ZJHealth.ZJHealth.repository.MedicalRecordRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final ZJUserRepository zjUserRepository;

    public void createDiagnosis(DiagnosisDto newDiagnosis) {
        Appointment appt = appointmentRepository.findById(newDiagnosis.getApptId()).get();
        MedicalRecord record = medicalRecordRepository.findByPatientId(appt.getPatient().getId());
        ZJUser patient = appt.getPatient();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisName(newDiagnosis.getDiagnosisName());
        diagnosis.setAppointment(appt);
        diagnosis.setRecord(record);
        diagnosisRepository.save(diagnosis);
        record.addDiagnosis(diagnosis);

        medicalRecordRepository.save(record);
        patient.setRecord(record);
        zjUserRepository.save(patient);
    }
}
