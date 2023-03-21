package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.model.Department;
import com.ZJHealth.ZJHealth.service.DepartmentService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@Data
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/create")
    public void createDept(@RequestBody Department dept) {
        departmentService.createDept(dept);

    }

    @GetMapping("/list")
    public List<Department> listall() {
        List<Department> depts = departmentService.listall();
        return depts;
    }

}
