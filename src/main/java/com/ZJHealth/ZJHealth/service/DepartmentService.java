package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.model.Department;
import com.ZJHealth.ZJHealth.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public void createDept(Department dept) {
        Department check = departmentRepository.findByNameIgnoreCase(dept.getName());
        if (check!=null) {
            throw new IllegalStateException("Department Exists");
        }
        else {
            departmentRepository.save(dept);
        }
    }

    public List<Department> listall() {
        return departmentRepository.findAll();
    }
}
