package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.MedicalRecordDto;
import com.ZJHealth.ZJHealth.model.*;
import com.ZJHealth.ZJHealth.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final TreatmentRepository treatmentRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final LabRepository labRepository;
    private final ZJUserRepository zjUserRepository;
    @Autowired
    private HttpServletRequest request;


    public void createRecord(ZJUser user) {
        MedicalRecord record = new MedicalRecord();
        record.setPatient(user);
        medicalRecordRepository.save(record);
    }

    public void addToRecord(MedicalRecordDto recordDto) {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        MedicalRecord record = medicalRecordRepository.findByPatientId(user.getId());
        recordDto.getDiagnosis().setRecord(record);
        recordDto.getTreatment().setRecord(record);
//        recordDto.getLab().setRecord(record);
        treatmentRepository.save(recordDto.getTreatment());
        diagnosisRepository.save(recordDto.getDiagnosis());
        labRepository.save(recordDto.getLab());
        record.addLab(recordDto.getLab());
        record.addDiagnosis(recordDto.getDiagnosis());
        record.addTreatment(recordDto.getTreatment());
        medicalRecordRepository.save(record);

    }


    public MedicalRecord viewRecord(Long id) {
        System.out.println(id);
        MedicalRecord record = medicalRecordRepository.findByPatientId(id);


        return record;
    }

    public MedicalRecord viewCurrentUsersRecord() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        return medicalRecordRepository.findByPatientId(user.getId());
    }
}
