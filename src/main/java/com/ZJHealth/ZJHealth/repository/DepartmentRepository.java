package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByNameIgnoreCase(String name);
}
