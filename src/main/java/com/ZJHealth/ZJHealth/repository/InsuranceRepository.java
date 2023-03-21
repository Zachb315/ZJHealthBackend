package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
