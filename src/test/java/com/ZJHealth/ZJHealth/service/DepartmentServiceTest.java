package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.model.Department;
import com.ZJHealth.ZJHealth.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;


    @BeforeEach
    void setUp() {
        departmentService = new DepartmentService(departmentRepository);
    }

    @Test
    void createDept() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("name");
        dept.setChair("chair");
        when(departmentRepository.findByNameIgnoreCase(dept.getName())).thenReturn(null);
        departmentService.createDept(dept);
        verify(departmentRepository).save(dept);
    }
    @Test
    void listall() {
        departmentService.listall();
        verify(departmentRepository).findAll();
    }
}