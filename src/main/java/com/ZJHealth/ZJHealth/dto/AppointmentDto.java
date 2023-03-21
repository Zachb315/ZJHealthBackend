package com.ZJHealth.ZJHealth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private String doctorEmail;
    private String type;
    private LocalDateTime date;
}
