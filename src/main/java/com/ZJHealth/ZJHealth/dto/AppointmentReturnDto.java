package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.AppointmentIntake;
import com.ZJHealth.ZJHealth.model.Diagnosis;
import com.ZJHealth.ZJHealth.model.ZJUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentReturnDto {
    private Long id;
    private LocalDateTime date;
    private int rating;
    private String review;
    private String type;
    private ZJUser patient;
    private ZJUser doctor;
    private AppointmentIntake intake;
    private Diagnosis diagnosis;

}
