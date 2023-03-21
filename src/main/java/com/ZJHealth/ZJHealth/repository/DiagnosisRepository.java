package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
