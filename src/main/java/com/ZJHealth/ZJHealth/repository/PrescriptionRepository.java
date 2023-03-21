package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByPatientId(Long id);

}
