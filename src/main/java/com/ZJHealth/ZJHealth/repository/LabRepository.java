package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {

    List<Lab> findAllByDoctorId(Long id);
}
