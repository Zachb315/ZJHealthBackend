package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.AppointmentIntake;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCreateDto {
    private AppointmentDto emails;
    private AppointmentIntake intake;
}
