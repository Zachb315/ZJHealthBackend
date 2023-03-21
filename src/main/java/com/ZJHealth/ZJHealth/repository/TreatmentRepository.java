package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}
