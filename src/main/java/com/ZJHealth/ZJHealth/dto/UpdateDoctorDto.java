package com.ZJHealth.ZJHealth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateDoctorDto {

    private String email;
    private String deptName;
    private String specialty;
    private int salary;
    private MultipartFile photoPath;
}

