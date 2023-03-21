package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.AppointmentIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentIntakeRepository extends JpaRepository<AppointmentIntake, Long> {
}
