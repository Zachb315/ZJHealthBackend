package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.MedicalRecord;
import com.ZJHealth.ZJHealth.model.ZJUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    MedicalRecord findByPatientId(Long id);
    List<MedicalRecord> findAllByPatientId(Long id);


}
